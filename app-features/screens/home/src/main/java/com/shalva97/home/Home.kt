package com.shalva97.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.ButtonLinkPrimary
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.ListChoice
import kiwi.orbit.compose.ui.controls.Text
import org.jellyfin.sdk.model.api.BaseItemDto
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun Home(navigateToLogin: () -> Unit = { }) {

    val viewModel = koinViewModel<HomeViewModel>()
    val homeState by viewModel.state.collectAsState()


    when (homeState) {
        is HomeState.Content -> {
            Content(
                modifier = Modifier
//                    .systemBarsPadding()
                    .fillMaxSize(),
                state = homeState as HomeState.Content,
                onLogoutClick = viewModel::logout,
                onItemClick = viewModel::openVideoByExternalApp
            )
        }
        HomeState.Loading -> {
//            Box(modifier = Modifier.fillMaxSize()) {
//                LinearIndeterminateProgressIndicator(Modifier.fillMaxWidth())
//            }
            Text(text = "asdasdas")
        }
        HomeState.NavigateToLogin -> {
            navigateToLogin.invoke()
            viewModel.navigationEventConsumed()
        }
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    state: HomeState.Content,
    onLogoutClick: () -> Unit = {},
    onItemClick: (BaseItemDto) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier.background(Color.Red)
    ) {
        val (logoutButton, content, bottomNavigation) = createRefs()

        ButtonLinkPrimary(onClick = {
            onLogoutClick()
        }, modifier = Modifier.constrainAs(logoutButton) {
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
                    height = Dimension.fillToConstraints
                }, verticalArrangement = Arrangement.Bottom
        ) {
            state.movies.forEach {
                ListChoice(onClick = { onItemClick(it) }) {
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