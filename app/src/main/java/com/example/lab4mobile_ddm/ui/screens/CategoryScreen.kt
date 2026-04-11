package com.example.lab4mobile_ddm.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab4mobile_ddm.R
import com.example.lab4mobile_ddm.data.Category
import com.example.lab4mobile_ddm.navigation.Destinations
import com.example.lab4mobile_ddm.ui.components.RecommendationCard
import com.example.lab4mobile_ddm.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryId: String,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    drawerState: DrawerState
) {
    android.util.Log.d("CategoryScreen", "Received categoryId: '$categoryId'")

    val category = Category.entries.find { it.id == categoryId }
    android.util.Log.d("CategoryScreen", "Found category: ${category?.titleResId}")

    val recommendations = sharedViewModel.getRecommendationsByCategory(categoryId)
    android.util.Log.d("CategoryScreen", "Recommendations count: ${recommendations.size}")
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(stringResource(R.string.home)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Destinations.Home.route) {
                                popUpTo(Destinations.Home.route) { inclusive = false }
                            }
                        }
                    )

                    for (cat in Category.entries) {
                        NavigationDrawerItem(
                            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                            label = { Text(stringResource(id = cat.titleResId)) },
                            selected = cat.id == categoryId,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(Destinations.Category.passCategoryId(cat.id))
                            }
                        )
                    }

                    HorizontalDivider()

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Info, contentDescription = null) },
                        label = { Text(stringResource(R.string.about)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Destinations.About.route)
                        }
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text(stringResource(R.string.settings)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Destinations.Settings.route)
                        }
                    )

                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(stringResource(category?.titleResId ?: R.string.categories))
                            Text(
                                text = "${recommendations.size} ${stringResource(R.string.places)}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (navController.previousBackStackEntry != null) {
                                navController.navigateUp()
                            } else {
                                // Косяк, Но лучше вернуться на главный
                                navController.popBackStack(Destinations.Home.route, inclusive = true)
                                navController.navigate(Destinations.Home.route)
                            }
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (recommendations.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_places),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(recommendations) { recommendation ->
                        RecommendationCard(
                            recommendation = recommendation,
                            onClick = {
                                sharedViewModel.selectRecommendation(recommendation)  // Это должно быть перед navigate
                                navController.navigate("detail/${recommendation.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}