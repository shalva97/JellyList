package com.shalva97.jellylist.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.illustrations.R.drawable
import kiwi.orbit.compose.ui.controls.*

@OptIn(ExperimentalLayoutApi::class)
@Preview(showSystemUi = true)
@Composable
fun LoginScreen() {

    val viewModel: LoginScreenViewModel = hiltViewModel()

    val discoveredServer = viewModel.foundServers.collectAsState(initial = emptyList())
    val errors = viewModel.errors.collectAsState()

    if (viewModel.loading.collectAsState().value) {
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
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    onClick = { viewModel.connectToServer(discoveredServer.value[index]) }) {
                    Text(text = discoveredServer.value[index].address ?: "",
                        modifier = Modifier.padding(5.dp))
                }
            }
        }

        TextField(value = viewModel.server.value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { viewModel.server.value = it; viewModel.clearError() },
            label = { Text(text = "Server") },
            leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
            trailingIcon = { Icon(painter = Icons.Close, contentDescription = "Clear") },
            onTrailingIconClick = { viewModel.server.value = ""; viewModel.clearError() },
            error = if (errors.value is Errors.BadServer) {
                @Composable { Text(text = "Can not connect to this server") }
            } else {
                null
            },
            maxLines = 1)

        ButtonPrimary(onClick = {
            viewModel.connectToServer()
        },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)) {
            Text(text = "Next")
        }

    }
}
