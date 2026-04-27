package com.example.lab4mobile_ddm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lab4mobile_ddm.navigation.NavigationState

class MainViewModel : ViewModel() {

    var navigationState by mutableStateOf(NavigationState())
        private set

    private var _scrollToTop by mutableStateOf(false)
    val scrollToTop: Boolean get() = _scrollToTop

    fun setSelectedBottomNavItem(index: Int) {
        navigationState = navigationState.copy(selectedBottomNavItem = index)
    }

    fun setDrawerOpen(isOpen: Boolean) {
        navigationState = navigationState.copy(isDrawerOpen = isOpen)
    }

    fun triggerScrollToTop() {
        _scrollToTop = true
    }

    fun consumeScrollToTop() {
        _scrollToTop = false
    }
}