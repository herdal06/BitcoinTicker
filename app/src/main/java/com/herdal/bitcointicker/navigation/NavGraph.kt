package com.herdal.bitcointicker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.herdal.bitcointicker.features.authentication.presentation.AuthenticationScreen
import com.herdal.bitcointicker.features.coin.presentation.coindetail.CoinDetailScreen
import com.herdal.bitcointicker.features.coin.presentation.mycoins.MyCoinsScreen
import com.herdal.bitcointicker.features.home.presentation.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Authentication
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.toString()
    ) {
        composable(Screen.Authentication.toString()) {
            AuthenticationScreen(onAuthSuccess = {
                navController.navigate(Screen.Home.toString())
            })
        }
        composable(Screen.Home.toString()) {
            HomeScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail(it))
            })
        }
        composable(Screen.MyCoins.toString()) {
            MyCoinsScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail(it))
            })
        }

        composable(Screen.CoinDetail.toString()) {
            val args = it.toRoute<Screen.CoinDetail>()
            CoinDetailScreen(id = args.id)
        }
    }
}