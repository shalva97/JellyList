package com.shalva97.jellylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shalva97.jellylist.ui.theme.JellyListTheme
import kiwi.orbit.compose.ui.controls.TextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JellyListTheme {
                JellyList()
            }
        }
    }
}

@Composable
fun JellyList() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "server") {
        composable("server") { ServerDiscovery() }
        composable("login") { Login() }
        composable("home") { Home() }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ServerDiscovery() {

    var expanded by remember { mutableStateOf("192.168.") }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Bottom) {
        TextField(value = expanded, onValueChange = { expanded = it }, label = {
            Text(text = "Server")
        }, modifier = Modifier
            .fillMaxWidth(), maxLines = 1)

        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Discover", modifier = Modifier)
        }
    }
}

@Composable
fun Home() {
    Text(text = "blaaaa home")
}
