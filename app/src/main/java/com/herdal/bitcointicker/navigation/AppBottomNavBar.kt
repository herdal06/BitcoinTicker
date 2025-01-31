package com.herdal.bitcointicker.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.herdal.bitcointicker.R

@Composable
fun AppBottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar {
        NavigationBarItem(
            selected = currentRoute == BottomNavItem.Home.screen.route,
            onClick = {
                navController.navigate(BottomNavItem.Home.screen.route)
            },
            icon = { Icon(BottomNavItem.Home.icon, contentDescription = stringResource(R.string.home)) },
            label = { Text(text = stringResource(R.string.home)) }
        )
        NavigationBarItem(
            selected = currentRoute == BottomNavItem.MyCoins.screen.route,
            onClick = { navController.navigate(BottomNavItem.MyCoins.screen.route) },
            icon = {
                Icon(
                    BottomNavItem.MyCoins.icon,
                    contentDescription = stringResource(R.string.my_coins)
                )
            },
            label = { Text(text = stringResource(R.string.my_coins)) }
        )
    }
}