package com.example.dark_x_timer_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dark_x_timer_kotlin.ui.counter.CounterScreen
import com.example.dark_x_timer_kotlin.ui.theme.Dark_x_timer_kotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dark_x_timer_kotlinTheme {
                CounterScreen()
            }
        }
    }
}

