package com.shalva97.jellylist.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.TextField

@Preview(showSystemUi = true)
@Composable
fun LoginScreen() {

    var server by remember { mutableStateOf("192.168.") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.Bottom) {
        TextField(value = server, onValueChange = { server = it }, label = {
            Text(text = "Server")
        }, modifier = Modifier
            .fillMaxWidth(), maxLines = 1)

        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Discover", modifier = Modifier)
        }
    }
}
