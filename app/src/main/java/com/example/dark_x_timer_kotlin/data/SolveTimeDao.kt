package com.example.dark_x_timer_kotlin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SolveTimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SolveTimeItem)

    @Update
    suspend fun update(item: SolveTimeItem)

    @Delete
    suspend fun delete(item: SolveTimeItem)

    @Query("SELECT * FROM times WHERE id = :id")
    fun getItem(id: Int): Flow<SolveTimeItem?>

    @Query("SELECT * FROM times ORDER BY name ASC")
    fun getItems(): Flow<List<SolveTimeItem>>

}