package com.shalva97.jellylist.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.consume

@Composable
fun Home(navController: NavHostController) {

    val viewModel = hiltViewModel<HomeViewModel>()

    LaunchedEffect(key1 = "nav") {
        viewModel.navigateToLogin.consume {
            navController.navigate("login")
        }
    }

    val movies = viewModel.movies.collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()) {

        movies.value.forEach {
            Text(text = it.originalTitle!!)
        }
    }
}