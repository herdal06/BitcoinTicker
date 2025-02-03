package com.herdal.bitcointicker.features.authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.herdal.bitcointicker.R

@Composable
fun SignUpTab(
    modifier: Modifier = Modifier,
    onSignUpClick: (String, String) -> Unit,
    isEmailAlreadyExists: Boolean = false
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            isError = isEmailAlreadyExists,
            supportingText = if (isEmailAlreadyExists) {
                { Text(stringResource(R.string.email_already_exists)) }
            } else null,
            modifier = modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = modifier.fillMaxWidth()
        )

        Button(
            onClick = { onSignUpClick(email, password) },
            modifier = modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && password.isNotEmpty() && !isEmailAlreadyExists
        ) {
            Text(stringResource(R.string.signup))
        }
    }
}