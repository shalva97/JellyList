package com.shalva97.jellylist.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TextField

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
        .padding(10.dp),
        verticalArrangement = Arrangement.Bottom) {

        LazyColumn {
            items(discoveredServer.value.size) { index ->
                Card(onClick = { viewModel.connectToServer(discoveredServer.value[index]) }) {
                    Text(text = discoveredServer.value[index].address ?: "")
                }
            }
        }
        Row {
            TextField(value = server,
                onValueChange = { server = it },
                label = { Text(text = "Server") },
                leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
                trailingIcon = { Icon(painter = Icons.Close, contentDescription = "Clear") },
                onTrailingIconClick = { server = "" },
                maxLines = 1)

            Button(onClick = { /*TODO*/ },
                enabled = false) {
                Text(text = "Next", modifier = Modifier)
            }
        }
    }
}
