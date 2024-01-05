package com.example.dark_x_timer_kotlin.ui.counter.deleteDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteViewModel(
    private val repository: SolveTimeRepo,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DeleteUIState())
    val uiState = _uiState.asStateFlow()

    fun setItem(item: SolveTimeItem) {
        _uiState.value = _uiState.value.copy(item = item)
    }

    fun delete() {
        viewModelScope.launch {
            repository.delete(_uiState.value.item!!)
        }
    }
}