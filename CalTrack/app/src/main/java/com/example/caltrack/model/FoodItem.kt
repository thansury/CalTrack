package com.example.caltrack.model

data class FoodItem(
    val id: String,
    val name: String,
    val caloriesPer100g: Double,
    val isLocal: Boolean = false
)
