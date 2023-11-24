package com.example.dark_x_timer_kotlin.ui

import com.example.dark_x_timer_kotlin.ui.counter.CounterViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class CounterViewModelTest {
    private val viewModel = CounterViewModel()

    @Test
    fun onStart() {
        val uiState = viewModel.uiState.value
        assertFalse(uiState.running)
        assertFalse(uiState.paused)
        assertEquals("00:00:00", uiState.time)
    }

}