package com.example.dark_x_timer_kotlin

import android.app.Application
import com.example.dark_x_timer_kotlin.data.AppContainer
import com.example.dark_x_timer_kotlin.data.AppDataContainer

class TimerApplication : Application(){
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}