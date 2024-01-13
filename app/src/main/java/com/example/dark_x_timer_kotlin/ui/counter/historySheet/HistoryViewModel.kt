package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.CubeType
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HistoryViewModel(
    private val repo: SolveTimeRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistorySheetState(itemList = emptyList()))

    val stateFlow = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistorySheetState(itemList = emptyList())
    )

    val cubeTypes = CubeType.entries.map { it.name }
    val selectedCubeTypes = mutableStateListOf<CubeType>()

    init {
        viewModelScope.launch {
            repo.getItems().collect {
                _uiState.value = _uiState.value.copy(itemList = it)
            }
        }
    }

    fun setForDeletion(item: SolveTimeItem) {
        _uiState.value =
            _uiState.value.copy(deleteItem = item, isDeleteConfirmationDialogVisible = true)
    }

    fun hideDeleteConfirmationDialog() {
        _uiState.value =
            _uiState.value.copy(deleteItem = null, isDeleteConfirmationDialogVisible = false)
    }

    fun showFilterDialog(){
        _uiState.value =
            _uiState.value.copy(isFilterDialogVisible = true)
    }
    fun hideFilterDialog(){
        _uiState.value =
            _uiState.value.copy(isFilterDialogVisible = false)
    }

    fun uncheckAllFilters(){
        selectedCubeTypes.clear()
    }

    fun calculateBestTime(): String {
        val sdf = SimpleDateFormat("mm:ss:SS", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        var bestTime = "99:59:99"
        _uiState.value.itemList.forEach {
            if (selectedCubeTypes.isEmpty() || selectedCubeTypes.contains(it.cubeType)) {
                val currentBestTime = sdf.parse(bestTime)
                val thisDateTime = sdf.parse(it.time)
                if (thisDateTime != null && thisDateTime.before(currentBestTime))
                    bestTime = it.time
            }
        }
        if (bestTime == "99:59:99")
            return "No Record"

        return bestTime
    }

    fun calculateAverageTime(): String {
        val sdf = SimpleDateFormat("mm:ss:SS", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        var totalTime = 0L
        var count = 0

        _uiState.value.itemList.forEach {
            if (selectedCubeTypes.isEmpty() || selectedCubeTypes.contains(it.cubeType)) {
                val thisDateTime = sdf.parse(it.time)
                thisDateTime?.let {
                    val thisTime = thisDateTime.time
                    totalTime += thisTime
                }
                count++
            }
        }

        if (count == 0)
            return "No Record"

        val avgTime = totalTime / count
        return sdf.format(Date(avgTime))
    }
}