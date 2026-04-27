package com.example.lab4mobile_ddm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.lab4mobile_ddm.viewmodel.MainViewModel
import com.example.lab4mobile_ddm.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    drawerState: DrawerState,
    onDrawerToggle: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.scrollToTop) {
        if (viewModel.scrollToTop) {
            listState.animateScrollToItem(0)
            viewModel.consumeScrollToTop()
        }
    }

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

                    for (category in Category.entries) {
                        NavigationDrawerItem(
                            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                            label = { Text(stringResource(category.titleResId)) },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(Destinations.Category.passCategoryId(category.id))
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
                    title = { Text(stringResource(R.string.app_name)) }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.recommendations_in_perm),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                for (category in Category.entries) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Destinations.Category.passCategoryId(category.id))
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(category.titleResId),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val itemCount = sharedViewModel.getRecommendationsByCategory(category.id).size
                                Text(
                                    text = "$itemCount ${stringResource(R.string.places)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = stringResource(R.string.view_all),
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    val recommendations = sharedViewModel.getRecommendationsByCategory(category.id)
                    items(recommendations.take(2)) { recommendation ->
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