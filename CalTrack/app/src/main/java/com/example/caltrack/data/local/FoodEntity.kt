package com.example.caltrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_foods")
data class FoodEntity(
    @PrimaryKey val id: String,
    val name: String,
    val caloriesPer100g: Double
)
