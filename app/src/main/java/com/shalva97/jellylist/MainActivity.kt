package com.shalva97.jellylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shalva97.jellylist.presentation.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkTheme = true
            val systemUiController = rememberSystemUiController()

            OrbitTheme(
                colors = if (isSystemInDarkTheme) darkColors() else lightColors(),
            ) {
                val color = OrbitTheme.colors.surface.subtle

                SideEffect {
                    systemUiController.setSystemBarsColor(color = color)
                }

                Scaffold {
                    JellyList()
                }
            }
        }
    }
}

@Composable
fun JellyList() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen() }
    }
}