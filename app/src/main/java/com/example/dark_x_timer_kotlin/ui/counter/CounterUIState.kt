package com.example.dark_x_timer_kotlin.ui.counter

data class CounterUIState(
    val time: String = "00:00:00",
    val running: Boolean = false,
    val paused: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
)
