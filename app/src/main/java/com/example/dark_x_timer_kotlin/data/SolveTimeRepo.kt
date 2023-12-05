package com.example.dark_x_timer_kotlin.data

import kotlinx.coroutines.flow.Flow

interface SolveTimeRepo {
    suspend fun insert(item: SolveTimeItem)

    suspend fun update(item: SolveTimeItem)

    suspend fun delete(item: SolveTimeItem)

    fun getItem(id: Int): Flow<SolveTimeItem?>

    fun getItems(): Flow<List<SolveTimeItem>>
}