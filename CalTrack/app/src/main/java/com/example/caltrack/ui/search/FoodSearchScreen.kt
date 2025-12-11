package com.example.caltrack.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caltrack.model.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchScreen(
    viewModel: com.example.caltrack.viewmodel.FoodSearchViewModel,
    onAddToLog: (FoodItem, Double) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val results by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.search(it)
            },
            label = { Text("搜索食物，如：苹果、米饭") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (isLoading) CircularProgressIndicator(Modifier.size(20.dp))
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(results.size) { index ->
                val food = results[index]
                FoodSearchResultItem(food = food, onAddClick = onAddToLog)
            }
        }
    }
}
