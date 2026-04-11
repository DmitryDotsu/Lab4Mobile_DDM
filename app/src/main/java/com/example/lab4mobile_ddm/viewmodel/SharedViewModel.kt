package com.example.lab4mobile_ddm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab4mobile_ddm.R
import com.example.lab4mobile_ddm.data.Recommendation
import com.example.lab4mobile_ddm.data.RecommendationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _selectedRecommendation = MutableStateFlow<Recommendation?>(null)
    val selectedRecommendation: StateFlow<Recommendation?> = _selectedRecommendation

    private val _currentCategoryId = MutableStateFlow("")
    val currentCategoryId: StateFlow<String> = _currentCategoryId

    fun selectRecommendation(recommendation: Recommendation) {
        // Используем titleResId вместо title
        val titleString = recommendation.titleResId
        android.util.Log.d("SharedViewModel", "Selected: Resource ID $titleString, Category: ${recommendation.category}")
        viewModelScope.launch {
            _selectedRecommendation.emit(recommendation)
            _currentCategoryId.emit(recommendation.category)
        }
    }

    fun setCurrentCategoryId(categoryId: String) {
        viewModelScope.launch {
            _currentCategoryId.emit(categoryId)
        }
    }

    fun getRecommendationsByCategory(categoryId: String): List<Recommendation> {
        android.util.Log.d("SharedViewModel", "getRecommendationsByCategory: $categoryId")
        return RecommendationData.getByCategory(categoryId)
    }

    fun getRecommendationById(id: String): Recommendation? {
        android.util.Log.d("SharedViewModel", "getRecommendationById: $id")
        val result = RecommendationData.getById(id)
        if (result != null) {
            android.util.Log.d("SharedViewModel", "Found: Resource ID ${result.titleResId}, Category: ${result.category}")
        } else {
            android.util.Log.d("SharedViewModel", "Found: null")
        }
        return result
    }
}