package com.example.weatherforecast.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.model.entity.Favorite
import com.example.weatherforecast.model.entity.Unit

@Database(entities = [Favorite::class,Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao
}