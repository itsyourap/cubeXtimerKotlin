package com.example.dark_x_timer_kotlin.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dark_x_timer_kotlin.TimerApplication
import com.example.dark_x_timer_kotlin.ui.counter.CounterViewModel
import com.example.dark_x_timer_kotlin.ui.counter.deleteDialog.DeleteViewModel
import com.example.dark_x_timer_kotlin.ui.counter.historySheet.HistoryViewModel
import com.example.dark_x_timer_kotlin.ui.counter.saveDialog.SaveViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CounterViewModel(
            )
        }
        initializer {
            SaveViewModel(
                timerApplicaiton().container.solveTimeRepo
            )
        }
        initializer {
            HistoryViewModel(
                timerApplicaiton().container.solveTimeRepo
            )
        }
        initializer {
            DeleteViewModel(
                timerApplicaiton().container.solveTimeRepo
            )
        }
    }
}

fun CreationExtras.timerApplicaiton(): TimerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TimerApplication)
