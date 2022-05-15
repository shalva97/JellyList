package com.shalva97.jellylist.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.TextField

@Preview(showSystemUi = true)
@Composable
fun LoginScreen() {

    val viewModel: LoginScreenViewModel = hiltViewModel()

    var server by remember { mutableStateOf("192.168.") }

    val discoveredServer = viewModel.foundServers.collectAsState(initial = emptyList())

    Column(modifier = Modifier
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

        TextField(value = server, onValueChange = { server = it }, label = {
            Text(text = "Server")
        }, modifier = Modifier
            .fillMaxWidth(), maxLines = 1)

        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End), enabled = false) {
            Text(text = "Next", modifier = Modifier)
        }
    }
}
