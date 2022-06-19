package com.shalva97.jellylist.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Home() {
    val viewModel = hiltViewModel<HomeViewModel>()

    viewModel.movies
}