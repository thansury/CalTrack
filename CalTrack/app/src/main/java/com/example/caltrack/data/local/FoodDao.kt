package com.example.caltrack.data.local

import androidx.room.*

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    @Query("SELECT * FROM cached_foods WHERE name LIKE '%' || :query || '%' LIMIT 20")
    suspend fun searchLocalFoods(query: String): List<FoodEntity>

    @Query("SELECT * FROM cached_foods WHERE id = :id")
    suspend fun getFoodById(id: String): FoodEntity?
}
