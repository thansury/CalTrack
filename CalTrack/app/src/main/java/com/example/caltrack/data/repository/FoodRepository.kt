package com.example.caltrack.data.repository

import com.example.caltrack.data.local.FoodDao
import com.example.caltrack.data.remote.UsdaApiService
import com.example.caltrack.model.FoodItem

class FoodRepository(
    private val foodDao: FoodDao,
    private val apiService: UsdaApiService
) {

    suspend fun searchFoods(query: String): List<FoodItem> {
        if (query.isBlank()) return emptyList()

        val local = foodDao.searchLocalFoods(query).map {
            FoodItem(it.id, it.name, it.caloriesPer100g, isLocal = true)
        }
        if (local.isNotEmpty()) return local

        return try {
            val response = apiService.searchFoods(query.trim())
            if (!response.isSuccessful) return emptyList()

            val remoteItems = response.body()?.foods?.mapNotNull { food ->
                val kcal = food.foodNutrients.find {
                    it.nutrientName == "Energy" && it.unitName == "KCAL"
                }?.value ?: return@mapNotNull null

                val item = FoodItem(
                    id = food.fdcId.toString(),
                    name = food.description,
                    caloriesPer100g = kcal
                )
                foodDao.insertFood(
                    com.example.caltrack.data.local.FoodEntity(
                        id = item.id,
                        name = item.name,
                        caloriesPer100g = item.caloriesPer100g
                    )
                )
                item
            } ?: emptyList()
            remoteItems
        } catch (e: Exception) {
            emptyList()
        }
    }
}
