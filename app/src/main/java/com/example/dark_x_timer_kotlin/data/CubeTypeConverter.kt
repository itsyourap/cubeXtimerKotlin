package com.example.dark_x_timer_kotlin.data
import androidx.room.TypeConverter

class CubeTypeConverter {

    @TypeConverter
    fun toCubeType(value: String) = enumValueOf<CubeType>(value)

    @TypeConverter
    fun fromCubeType(value: CubeType) = value.name
}