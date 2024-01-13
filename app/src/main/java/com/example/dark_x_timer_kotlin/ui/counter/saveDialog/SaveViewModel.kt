package com.example.dark_x_timer_kotlin.ui.counter.saveDialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.CubeType
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class SaveViewModel(
    private val repository: SolveTimeRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(SaveUIState())
    val uiState = _uiState.asStateFlow()
    private var items: MutableList<SolveTimeItem> = mutableListOf()

    private var bestDateTime: Date? = null
    private var worstDateTime: Date? = null
    private var avgTime: Date? = null
    val informationMessage: MutableState<String?> = mutableStateOf("")

    init {
        viewModelScope.launch {
            repository.getItems().collect {
                items.clear()
                items.addAll(it)
                calculateBestWorstAvgTime()
            }
        }
    }

    val cubeTypes = CubeType.entries.map { it.name }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onCubeTypeChanged(cubeType: String) {
        _uiState.value = _uiState.value.copy(cubeType = CubeType.valueOf(cubeType))
        calculateBestWorstAvgTime()
    }

    fun setTime(time: String) {
        _uiState.value = _uiState.value.copy(time = time)
        calculateBestWorstAvgTime()
    }

    fun save() {
        viewModelScope.launch {
            repository.insert(uiState.value.toItem())
        }
    }

    fun toggleDropdown() {
        _uiState.value = _uiState.value.copy(isDropdownExpanded = !uiState.value.isDropdownExpanded)
    }

    private fun calculateBestWorstAvgTime() {
        val sdf = SimpleDateFormat("mm:ss:SS", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        var totalTime = 0L
        var count = 0

        items.forEach {
            if (_uiState.value.cubeType == it.cubeType) {
                val thisDateTime = sdf.parse(it.time)
                if (bestDateTime == null)
                    bestDateTime = thisDateTime

                if (worstDateTime == null)
                    worstDateTime = thisDateTime

                if (thisDateTime != null && thisDateTime.before(bestDateTime))
                    bestDateTime = thisDateTime

                if (thisDateTime != null && thisDateTime.after(worstDateTime))
                    worstDateTime = thisDateTime

                thisDateTime?.let {
                    val thisTime = thisDateTime.time
                    totalTime += thisTime
                }

                count++
            }
        }

        if (count != 0)
            avgTime = Date(totalTime / count)
        else {
            bestDateTime = null
            worstDateTime = null
            avgTime = null
        }

        informationMessage.value = getInformationBasedOnRecord()
    }
    private fun getInformationBasedOnRecord(): String? {
        val sdf = SimpleDateFormat("mm:ss:SS", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val currentTime = sdf.parse(uiState.value.time)

        if (currentTime == null || bestDateTime == null || worstDateTime == null || avgTime == null)
            return null

        return if (currentTime.before(bestDateTime))
            BROKEN_ALL_RECORDS
        else if (currentTime.before(avgTime))
            BETTER_THAN_AVERAGE
        else if (currentTime.after(worstDateTime))
            TOO_SLOW
        else if (currentTime.after(avgTime))
            WORSE_THAN_AVERAGE
        else
            null
    }
}

fun SaveUIState.toItem(): SolveTimeItem {
    return SolveTimeItem(
        name = name.ifEmpty { "Untitled" },
        time = time,
        cubeType = cubeType,
        date = System.currentTimeMillis()
    )
}