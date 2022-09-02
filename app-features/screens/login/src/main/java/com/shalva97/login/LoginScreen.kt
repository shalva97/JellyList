package com.shalva97.login

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recent_servers.presentation.RecentServers
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.illustrations.R.drawable
import kiwi.orbit.compose.ui.controls.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(backToHome: () -> Boolean) {

    LocalSoftwareKeyboardController.current?.show()

    val viewModel: LoginScreenViewModel = koinViewModel()

    LaunchedEffect(key1 = "navv") {
        viewModel.navigateToHome.consumeEach {
            backToHome.invoke()
        }
    }

    BackHandler(enabled = true) {}

    val errors = viewModel.errors

    if (viewModel.loading.value) {
        LinearIndeterminateProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

    Image(painter = painterResource(id = drawable.il_orbit_compass_save_on_booking),
        contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp))

    Column(modifier = Modifier
        .systemBarsPadding()
        .fillMaxSize()
        .imePadding()
        .imeNestedScroll()
        .scrollable(rememberScrollState(), Orientation.Vertical)
        .padding(10.dp),
        verticalArrangement = Arrangement.Bottom) {

        RecentServers {
            viewModel.onRecentServerClicked(it.address)
        }

        TextField(value = viewModel.server.value,
            modifier = Modifier
                .fillMaxWidth()
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                        viewModel.onNextButtonClicked()
                        return@onKeyEvent true
                    }
                    false
                },
            onValueChange = { viewModel.server.value = it; viewModel.clearError() },
            enabled = viewModel.showAuthFields.value.not(),
            label = { Text(text = "Server") },
            leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
            trailingIcon = { Icon(painter = Icons.Close, contentDescription = "Clear") },
            onTrailingIconClick = {
                viewModel.serverTrailingIconClicked()
            },
            error = if (errors.value is Errors.BadServer) {
                @Composable { Text(text = "Can not connect to this server") }
            } else null,
            keyboardOptions = KeyboardOptions(autoCorrect = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onNext = {
                viewModel.onNextButtonClicked()
            }),
            maxLines = 1)

        if (viewModel.showAuthFields.value) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                label = { Text(text = "Login") },
                value = viewModel.authDetails.username.value,
                onValueChange = { viewModel.authDetails.username.value = it },
                leadingIcon = {
                    Icon(painter = Icons.AccountCircle, contentDescription = "profile icon")
                })
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                leadingIcon = { Icon(Icons.Security, contentDescription = null) },
                label = { Text(text = "Password") },
                value = viewModel.authDetails.password.value,
                onValueChange = { viewModel.authDetails.password.value = it },
            )
        }

        Row(modifier = Modifier
            .align(Alignment.End)
            .padding(top = 10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            ButtonSecondary(modifier = Modifier.padding(end = 10.dp), onClick = { /*TODO*/ }) {
                Text(text = "Discover")
            }
            ButtonPrimary(
                modifier = Modifier
                    .height(42.dp)
                    .width(64.dp),
                onClick = {
                    viewModel.onNextButtonClicked()
                },
            ) {
                Text(text = "Next")
            }
        }


    }
}
