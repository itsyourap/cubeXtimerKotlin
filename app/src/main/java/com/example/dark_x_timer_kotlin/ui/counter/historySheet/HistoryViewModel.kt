package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dark_x_timer_kotlin.data.SolveTimeRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    repo: SolveTimeRepo
) : ViewModel() {
    val stateFlow = repo.getItems().map {
        HistorySheetState(
            itemList = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HistorySheetState(itemList = emptyList())
    )
}