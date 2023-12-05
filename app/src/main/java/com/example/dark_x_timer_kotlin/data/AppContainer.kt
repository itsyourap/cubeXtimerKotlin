package com.example.dark_x_timer_kotlin.data

import android.content.Context

interface AppContainer {
    val solveTimeRepo: SolveTimeRepo
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val solveTimeRepo: SolveTimeRepo by lazy {
        OfflineSolveTimeRepo(
            SolveTimeDatabase.getInstance(
                context
            ).itemDao()
        )
    }
}