package com.shalva97.jellylist.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.channels.consume

@Composable
@Preview
fun Home(navigateToHome: () -> Unit = { }) {

    val viewModel = hiltViewModel<HomeViewModel>()

    LaunchedEffect(key1 = "nav") {
        viewModel.navigateToLogin.consume {
            navigateToHome.invoke()
        }
    }

    val movies = viewModel.movies.collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()) {

        movies.value.forEach {
            Text(text = it.originalTitle ?: "asdf")
        }
    }
}