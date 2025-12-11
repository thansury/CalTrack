package com.example.caltrack.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class FoodSearchResponse(
    val foods: List<UsdaFood>
)

data class UsdaFood(
    val fdcId: Int,
    val description: String,
    val foodNutrients: List<Nutrient>
)

data class Nutrient(
    val nutrientName: String,
    val value: Double,
    val unitName: String
)

interface UsdaApiService {
    @GET("foods/search")
    suspend fun searchFoods(
        @Query("query") query: String,
        @Query("pageSize") pageSize: Int = 10
    ): Response<FoodSearchResponse>
}
