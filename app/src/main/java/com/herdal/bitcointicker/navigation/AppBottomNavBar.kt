package com.herdal.bitcointicker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
    BottomAppBar  {
        NavigationBarItem(
            selected = currentRoute == BottomNavItem.Home.route.toString(),
            onClick = {
                navController.navigate(Screen.Home.toString())
            },
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(text = stringResource(R.string.home)) }
        )
        NavigationBarItem(
            selected = currentRoute == BottomNavItem.MyCoins.route.toString(),
            onClick = { navController.navigate(Screen.MyCoins.toString()) },
            icon = {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.my_coins)
                )
            },
            label = { Text(text = stringResource(R.string.my_coins)) }
        )
    }
}