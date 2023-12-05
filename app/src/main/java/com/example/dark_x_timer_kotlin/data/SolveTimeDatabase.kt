package com.example.dark_x_timer_kotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.TypeConverters


@Database(entities = [SolveTimeItem::class], version = 1, exportSchema = false)
@TypeConverters(CubeTypeConverter::class)
abstract class SolveTimeDatabase : RoomDatabase() {

    abstract fun itemDao(): SolveTimeDao

    companion object {
        private const val DATABASE_NAME = "solve-time-db"

        @Volatile
        private var INSTANCE: SolveTimeDatabase? = null

        fun getInstance(context: Context): SolveTimeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    SolveTimeDatabase::class.java,
                    DATABASE_NAME,
                ).fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}