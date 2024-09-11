package com.kmcoding.weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val url: String,
    var timestamp: Long? = 0L,
)
