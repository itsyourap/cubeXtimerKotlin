package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.SolveTimeItem
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repo: SolveTimeRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistorySheetState(itemList = emptyList()))

    val stateFlow = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistorySheetState(itemList = emptyList())
    )

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
}