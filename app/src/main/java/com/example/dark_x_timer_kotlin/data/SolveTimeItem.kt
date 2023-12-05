package com.example.dark_x_timer_kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "times")
data class SolveTimeItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String,
    val date: Long,
    val cubeType: CubeType
)