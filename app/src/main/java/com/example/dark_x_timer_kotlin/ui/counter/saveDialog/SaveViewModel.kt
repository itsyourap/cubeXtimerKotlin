package com.example.dark_x_timer_kotlin.ui.counter.saveDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.CubeType
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SaveViewModel(
    private val repository: SolveTimeRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(SaveUIState())
    val uiState = _uiState.asStateFlow()

    val cubeTypes = CubeType.entries.map { it.name }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onCubeTypeChanged(cubeType: String) {
        _uiState.value = _uiState.value.copy(cubeType = CubeType.valueOf(cubeType))
    }

    fun setTime(time: String) {
        _uiState.value = _uiState.value.copy(time = time)
    }

    fun save() {
        viewModelScope.launch {
            repository.insert(uiState.value.toItem())
        }
    }

    fun toggleDropdown() {
        _uiState.value = _uiState.value.copy(isDropdownExpanded = !uiState.value.isDropdownExpanded)
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