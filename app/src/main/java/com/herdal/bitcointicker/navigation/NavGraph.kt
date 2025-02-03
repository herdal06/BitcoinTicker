package com.herdal.bitcointicker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.herdal.bitcointicker.features.authentication.presentation.AuthenticationScreen
import com.herdal.bitcointicker.features.coin.presentation.coindetail.CoinDetailScreen
import com.herdal.bitcointicker.features.coin.presentation.mycoins.MyCoinsScreen
import com.herdal.bitcointicker.features.home.presentation.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Authentication.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Authentication.route) {
            AuthenticationScreen(onSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Authentication.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail.createRoute(it.toString()))
            })
        }

        composable(Screen.MyCoins.route) {
            MyCoinsScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail.createRoute(it.toString()))
            })
        }

        composable(Screen.CoinDetail.route) {
            CoinDetailScreen()
        }
    }
}