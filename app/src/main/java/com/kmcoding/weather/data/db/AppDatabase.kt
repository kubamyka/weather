package com.kmcoding.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kmcoding.weather.domain.model.Location

@Database(entities = [Location::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
