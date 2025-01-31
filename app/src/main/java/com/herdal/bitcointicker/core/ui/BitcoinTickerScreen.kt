package com.herdal.bitcointicker.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.herdal.bitcointicker.core.ui.components.TopAppBar
import com.herdal.bitcointicker.navigation.AppBottomNavBar
import com.herdal.bitcointicker.navigation.Screen
import com.herdal.bitcointicker.navigation.SetupNavGraph

@Composable
fun BitcoinTickerScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(onSignOut = {
                navController.navigate(Screen.Authentication) {
                    popUpTo(Screen.Home) { inclusive = true }
                }
            })
        },
        bottomBar = {
            AppBottomNavBar(navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SetupNavGraph(navController = navController)
        }
    }
}