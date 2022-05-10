package com.shalva97.jellylist

import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shalva97.jellylist.presentation.LoginScreen
import com.shalva97.jellylist.ui.theme.JellyListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JellyListTheme {
                JellyList()
            }
        }
        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        id
    }
}

@Composable
fun JellyList() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen() }
    }
}