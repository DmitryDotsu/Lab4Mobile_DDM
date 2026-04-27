package com.example.lab4mobile_ddm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lab4mobile_ddm.R
import com.example.lab4mobile_ddm.navigation.Destinations
import com.example.lab4mobile_ddm.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    recommendationId: String,
    categoryId: String,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    drawerState: DrawerState
) {
    val recommendation by sharedViewModel.selectedRecommendation.collectAsState()
    val currentRecommendation = recommendation ?: sharedViewModel.getRecommendationById(recommendationId)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
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

                    for (category in com.example.lab4mobile_ddm.data.Category.entries) {
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
                    title = {
                        currentRecommendation?.let {
                            Text(stringResource(it.titleResId))
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    currentRecommendation?.imageResId?.let { imageRes ->
                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = currentRecommendation?.let { stringResource(it.titleResId) },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        currentRecommendation?.let { rec ->
                            Text(
                                text = stringResource(rec.titleResId),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(rec.descriptionResId),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "${stringResource(R.string.address)}: ${stringResource(rec.addressResId)}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${stringResource(R.string.working_hours)}: ${rec.workingHours}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        text = "${stringResource(R.string.rating)}: ${rec.rating} ★",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    currentRecommendation?.let { rec ->
                        rec.imageResId?.let { imageRes ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = stringResource(rec.titleResId),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(rec.titleResId),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(rec.descriptionResId),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "${stringResource(R.string.address)}: ${stringResource(rec.addressResId)}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "${stringResource(R.string.working_hours)}: ${rec.workingHours}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        text = "${stringResource(R.string.rating)}: ${rec.rating} ★",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}