package com.shalva97.jellylist.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun Home() {
    val viewModel = hiltViewModel<HomeViewModel>()

    Text(text = "123asdqwe")
}