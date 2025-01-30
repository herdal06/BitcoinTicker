package com.herdal.bitcointicker.features.authentication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.herdal.bitcointicker.R
import com.herdal.bitcointicker.core.domain.UiState
import com.herdal.bitcointicker.core.ui.components.ErrorDialog
import com.herdal.bitcointicker.core.ui.components.LoadingScreen
import com.herdal.bitcointicker.features.authentication.presentation.components.LoginTab
import com.herdal.bitcointicker.features.authentication.presentation.components.SignUpTab

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onSuccess: (String) -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(R.string.login), stringResource(R.string.signup))

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        when (tabIndex) {
            0 -> LoginTab(
                onLoginClick = { email, password ->
                    viewModel.loginUser(email, password)
                }
            )

            1 -> SignUpTab(
                onSignUpClick = { email, password ->
                    viewModel.registerUser(email, password)
                }
            )
        }
    }

    when (val loginState = authState.loginState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> loginState.data?.let { user -> onSuccess(user.email.orEmpty()) }
        is UiState.Error -> ErrorDialog(loginState.message, onDismiss = {})
    }

    when (val registerState = authState.registerState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> registerState.data?.let { user -> onSuccess(user.email.orEmpty()) }
        is UiState.Error -> ErrorDialog(registerState.message, onDismiss = {})
    }
}