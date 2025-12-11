package com.example.caltrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.caltrack.data.local.AppDatabase
import com.example.caltrack.data.remote.UsdaApiService
import com.example.caltrack.data.repository.FoodRepository
import com.example.caltrack.ui.search.FoodSearchScreen
import com.example.caltrack.viewmodel.FoodSearchViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(this)
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.nal.usda.gov/fdc/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsdaApiService::class.java)

        val repository = FoodRepository(database.foodDao(), apiService)
        val viewModel = FoodSearchViewModel(repository)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FoodSearchScreen(viewModel) { food, totalCal ->
                        println("✅ Added: $${food.name} → $$totalCal kcal")
                    }
                }
            }
        }
    }
}
