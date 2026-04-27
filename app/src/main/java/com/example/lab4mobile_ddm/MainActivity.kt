package com.example.lab4mobile_ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab4mobile_ddm.navigation.NavigationGraph
import com.example.lab4mobile_ddm.ui.theme.Lab4Mobile_DDMTheme
import com.example.lab4mobile_ddm.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Mobile_DDMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel: MainViewModel = viewModel()
                    NavigationGraph(
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}