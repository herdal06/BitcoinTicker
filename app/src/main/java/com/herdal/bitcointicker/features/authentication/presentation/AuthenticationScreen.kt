package com.herdal.bitcointicker.features.authentication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.padding(4.dp),
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .clip(RoundedCornerShape(16.dp)),
                        height = 40.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                },
                containerColor = Color.Transparent
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (tabIndex == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                fontWeight = if (tabIndex == index) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Normal
                                }
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        modifier = Modifier.height(40.dp)
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
            is UiState.Success -> loginState.data?.let { user ->
                onSuccess(user.email.orEmpty())
            }

            is UiState.Error -> ErrorDialog(
                message = loginState.message
            )
        }

        when (val registerState = authState.registerState) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> registerState.data?.let { user ->
                onSuccess(user.email.orEmpty())
            }

            is UiState.Error -> ErrorDialog(
                message = registerState.message
            )
        }
    }
}