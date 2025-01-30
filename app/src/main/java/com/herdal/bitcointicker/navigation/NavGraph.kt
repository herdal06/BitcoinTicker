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
    startDestination: Screen = Screen.Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Authentication> {
            AuthenticationScreen(onAuthSuccess = {
                navController.navigate(Screen.Home)
            })
        }
        composable<Screen.Home> {
            HomeScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail(it))
            })
        }
        composable<Screen.MyCoins> {
            MyCoinsScreen(onClickCoin = {
                navController.navigate(Screen.CoinDetail(it))
            })
        }

        composable<Screen.CoinDetail> {
            val args = it.toRoute<Screen.CoinDetail>()
            CoinDetailScreen(id = args.id)
        }
    }
}