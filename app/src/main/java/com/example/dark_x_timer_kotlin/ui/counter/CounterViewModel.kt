package com.example.dark_x_timer_kotlin.ui.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.properties.Delegates

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUIState())

    private var startTime = 0L
    private var elapsedTime = 0L

    private var _isRunning = false
    private var _isPaused = false

    val uiState = _uiState.asStateFlow()


    init {
        startTime = System.currentTimeMillis()
        Timer(
            "Timer",
            false
        ).scheduleAtFixedRate(0, 10) {
            updateState()
        }
    }

    private fun updateState() {
        if (_isRunning) {
            if (!_isPaused) {
                elapsedTime = System.currentTimeMillis() - startTime
                _uiState.value = _uiState.value.copy(time = formatTime(elapsedTime))
            }
        }
    }

    private fun formatTime(time: Long): String {
        val tenMillis = (time / 10) % 100
        val seconds = (time / 1000) % 60
        val minutes = (time / 1000) / 60

        return "%02d:%02d:%02d".format(minutes, seconds, tenMillis)
    }

    fun onStart() {
        startTime = System.currentTimeMillis()
        _isRunning = true
        _uiState.value = _uiState.value.copy(running = _isRunning)
    }

    fun onPause() {
        _isPaused = true
        _uiState.value = _uiState.value.copy(paused = _isPaused)
    }

    fun onResume() {
        startTime = System.currentTimeMillis() - elapsedTime
        _isPaused = false
        _uiState.value = _uiState.value.copy(paused = _isPaused)
    }

    fun onReset() {
        _isPaused = false
        _isRunning = false
        elapsedTime = 0L
        _uiState.value = _uiState.value.copy(
            running = _isRunning,
            paused = _isPaused,
            time = formatTime(elapsedTime),
        )
    }

}