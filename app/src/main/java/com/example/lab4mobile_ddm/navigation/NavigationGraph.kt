package com.example.lab4mobile_ddm.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab4mobile_ddm.R
import com.example.lab4mobile_ddm.ui.screens.AboutScreen
import com.example.lab4mobile_ddm.ui.screens.CategoryScreen
import com.example.lab4mobile_ddm.ui.screens.DetailScreen
import com.example.lab4mobile_ddm.ui.screens.HomeScreen
import com.example.lab4mobile_ddm.ui.screens.SettingsScreen
import com.example.lab4mobile_ddm.viewmodel.MainViewModel
import com.example.lab4mobile_ddm.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

sealed class BottomBarState {
    object HomeScreen : BottomBarState()
    object CategoryScreen : BottomBarState()
    object DetailScreen : BottomBarState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val toggleDrawer: () -> Unit = {
        scope.launch {
            if (drawerState.isOpen) {
                drawerState.close()
                viewModel.setDrawerOpen(false)
            } else {
                drawerState.open()
                viewModel.setDrawerOpen(true)
            }
        }
    }

    val bottomBarState = when {
        currentRoute == Destinations.Home.route -> BottomBarState.HomeScreen
        currentRoute?.startsWith("category/") == true -> BottomBarState.CategoryScreen
        currentRoute?.startsWith("detail/") == true -> BottomBarState.DetailScreen
        else -> null
    }

    Scaffold(
        bottomBar = {
            when (bottomBarState) {
                BottomBarState.HomeScreen -> {
                    HomeScreenBottomBar(navController, viewModel, toggleDrawer)
                }
                BottomBarState.CategoryScreen -> {
                    toggleDrawer.CategoryScreenBottomBar(navController)
                }
                BottomBarState.DetailScreen -> {
                    sharedViewModel.DetailScreenBottomBar(
                        navController,
                        toggleDrawer
                    )
                }
                null -> {}
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Destinations.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destinations.Home.route) {
                HomeScreen(
                    navController = navController,
                    viewModel = viewModel,
                    sharedViewModel = sharedViewModel,
                    drawerState = drawerState,
                    onDrawerToggle = toggleDrawer
                )
            }

            composable(
                route = Destinations.Category.route,
                arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "coffee"
                CategoryScreen(
                    categoryId = categoryId,
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    drawerState = drawerState
                )
            }

            composable(
                route = Destinations.Detail.route,
                arguments = listOf(navArgument("recommendationId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recommendationId = backStackEntry.arguments?.getString("recommendationId") ?: "1"
                val recommendation = sharedViewModel.getRecommendationById(recommendationId)
                DetailScreen(
                    recommendationId = recommendationId,
                    categoryId = recommendation?.category ?: "coffee",
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    drawerState = drawerState,
                    onDrawerToggle = toggleDrawer
                )
            }

            composable(Destinations.About.route) {
                AboutScreen(navController = navController)
            }

            composable(Destinations.Settings.route) {
                SettingsScreen(navController = navController)
            }
        }
    }
}

@Composable
fun HomeScreenBottomBar(
    navController: NavHostController,
    viewModel: MainViewModel,
    onMenuClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu)) },
            label = { Text(stringResource(R.string.menu)) },
            selected = false,
            onClick = onMenuClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            onClick = { viewModel.triggerScrollToTop() }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = stringResource(R.string.about)) },
            label = { Text(stringResource(R.string.about)) },
            selected = false,
            onClick = { navController.navigate(Destinations.About.route) }
        )
    }
}

// BottomBar для экрана категории (Экран 3)
@Composable
fun (() -> Unit).CategoryScreenBottomBar(
    navController: NavHostController
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu)) },
            label = { Text(stringResource(R.string.menu)) },
            selected = false,
            onClick = this@CategoryScreenBottomBar
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            onClick = {
                navController.popBackStack(Destinations.Home.route, inclusive = true)
                navController.navigate(Destinations.Home.route) { launchSingleTop = true }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = stringResource(R.string.about)) },
            label = { Text(stringResource(R.string.about)) },
            selected = false,
            onClick = { navController.navigate(Destinations.About.route) }
        )
    }
}

// BottomBar для экрана деталей (Экран 4)
@Composable
fun SharedViewModel.DetailScreenBottomBar(
    navController: NavHostController,
    onMenuClick: () -> Unit
) {
    val currentCategoryId by currentCategoryId.collectAsState()

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu)) },
            label = { Text(stringResource(R.string.menu)) },
            selected = false,
            onClick = onMenuClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            onClick = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Category, contentDescription = stringResource(R.string.category)) },
            label = { Text(stringResource(R.string.category)) },
            selected = false,
            onClick = {
                if (currentCategoryId.isNotEmpty()) {
                    navController.navigate("category/$currentCategoryId") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}