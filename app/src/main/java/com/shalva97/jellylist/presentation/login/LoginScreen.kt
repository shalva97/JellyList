package com.shalva97.jellylist.presentation.login

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.illustrations.R.drawable
import kiwi.orbit.compose.ui.controls.*
import kotlinx.coroutines.channels.consume

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    val viewModel: LoginScreenViewModel = hiltViewModel()

    LaunchedEffect(key1 = "nav") {
        viewModel.navigateToHome.consume {
            navController.popBackStack("Home", true)
        }
    }

    val discoveredServer = viewModel.foundServers.collectAsState(initial = emptyList())
    val errors = viewModel.errors
    val previousServers = viewModel.previousServers.collectAsState(initial = emptyList())

    if (viewModel.loading.value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
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

        LazyColumn {
            items(discoveredServer.value.size) { index ->
                if (index == 0) {
                    Text(text = "Discovered")
                }
                ListChoice(onClick = {
                    viewModel.onDiscoveredServerClicked(discoveredServer.value[index])
                }) {
                    Text(text = discoveredServer.value[index].address,
                        modifier = Modifier.padding(5.dp))
                }
            }

            items(previousServers.value.size) { index ->
                if (index == 0) {
                    Text(text = "Recent Servers")
                }
                ListChoice(onClick = {
                    viewModel.onRecentServerClicked(previousServers.value.elementAt(index))
                }) {
                    Text(text = previousServers.value.elementAt(index))
                }
            }

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
                .padding(top = 10.dp),
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
                onClick = {
                    viewModel.onNextButtonClicked()
                },
            ) {
                Text(text = "Next")
            }
        }


    }
}
