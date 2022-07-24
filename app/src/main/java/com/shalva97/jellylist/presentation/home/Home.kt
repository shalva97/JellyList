package com.shalva97.jellylist.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kiwi.orbit.compose.ui.controls.ListChoice
import kotlinx.coroutines.channels.consumeEach
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun Home(navigateToLogin: () -> Unit = { }) {

    val viewModel = koinViewModel<HomeViewModel>()

    LaunchedEffect(key1 = "nav") {
        viewModel.navigateToLogin.consumeEach {
            navigateToLogin.invoke()
        }
    }

    val movies = viewModel.movies.collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .systemBarsPadding()) {

        movies.value.forEach {
            ListChoice(onClick = { viewModel.openVideoByExternalApp(it) }) {
                Text(text = it.name ?: "Unknown")
            }
        }
    }
}

@Preview
@Composable
fun Blah() {
    Text(text = "asdf")
}