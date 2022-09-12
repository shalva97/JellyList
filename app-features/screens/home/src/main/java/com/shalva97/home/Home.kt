package com.shalva97.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.ButtonLinkPrimary
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.ListChoice
import kiwi.orbit.compose.ui.controls.Text
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val (logoutButton, content, bottomNavigation) = createRefs()

        ButtonLinkPrimary(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(logoutButton) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }) {
            Text(text = "logout")
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .constrainAs(content) {
                    top.linkTo(logoutButton.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(bottomNavigation.top)
                }, verticalArrangement = Arrangement.Bottom
        ) {
            movies.value.forEach {
                ListChoice(onClick = { viewModel.openVideoByExternalApp(it) }) {
                    Text(text = it.name ?: "Unknown")
                }
            }
        }

        BottomNavigation(
            Modifier
                .fillMaxWidth()
                .constrainAs(bottomNavigation) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ) {
            BottomNavigationItem(selected = true, onClick = { /*TODO*/ }, icon = {
                Icon(painter = Icons.List, contentDescription = null)
            })
            BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Icon(painter = Icons.Child, contentDescription = null)
            })
        }
    }
}
