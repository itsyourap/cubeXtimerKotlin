package com.example.dark_x_timer_kotlin.ui.counter.saveDialog

import com.example.dark_x_timer_kotlin.data.CubeType

data class SaveUIState (
    val name: String = "",
    val time: String = "00:00:00",
    val cubeType: CubeType = CubeType.THREE,
    val isDropdownExpanded : Boolean = false,
)