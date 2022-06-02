package com.shalva97.jellylist.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.*

@OptIn(ExperimentalLayoutApi::class)
@Preview(showSystemUi = true)
@Composable
fun LoginScreen() {

    val viewModel: LoginScreenViewModel = hiltViewModel()

    var server by remember { mutableStateOf("192.168.") }

    val discoveredServer = viewModel.foundServers.collectAsState(initial = emptyList())

    if (viewModel.loading.collectAsState().value) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

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
                    .padding(10.dp),
                    onClick = { viewModel.connectToServer(discoveredServer.value[index]) }) {
                    Text(text = discoveredServer.value[index].address ?: "")
                }
            }
        }

        TextField(value = server,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { server = it },
            label = { Text(text = "Server") },
            leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
            trailingIcon = { Icon(painter = Icons.Close, contentDescription = "Clear") },
            onTrailingIconClick = { server = "" },
            maxLines = 1)

        ButtonPrimary(onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)) {
            Text(text = "Next")
        }

    }
}
