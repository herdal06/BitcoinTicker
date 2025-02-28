package com.herdal.bitcointicker.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.herdal.bitcointicker.core.ui.components.TopAppBar
import com.herdal.bitcointicker.navigation.AppBottomNavBar
import com.herdal.bitcointicker.navigation.Screen
import com.herdal.bitcointicker.navigation.SetupNavGraph

@Composable
fun BitcoinTickerScreen(
    navController: NavHostController,
    viewModel: BitcoinTickerViewModel = hiltViewModel()
) {
    val startScreen by rememberSaveable { mutableStateOf(viewModel.getSetupScreen()) }
    val currentDestination by navController.currentBackStackEntryAsState()
    val shouldShowBottomBar = when (currentDestination?.destination?.route) {
        Screen.Authentication.route, Screen.CoinDetail.route -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomNavBar(navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SetupNavGraph(
                navController = navController,
                startDestination = startScreen
            )
        }
    }
}