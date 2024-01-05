package com.example.dark_x_timer_kotlin.ui.counter.historySheet

import com.example.dark_x_timer_kotlin.data.SolveTimeItem


data class HistorySheetState(
    val itemList: List<SolveTimeItem>,
    val isDeleteConfirmationDialogVisible: Boolean = false,
    var deleteItem: SolveTimeItem? = null
)
