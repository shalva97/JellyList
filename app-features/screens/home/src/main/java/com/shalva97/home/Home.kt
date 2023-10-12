package com.shalva97.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonLinkPrimary
import kiwi.orbit.compose.ui.controls.LinearIndeterminateProgressIndicator
import kiwi.orbit.compose.ui.controls.ListChoice
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
@Preview
fun Home(navigateToLogin: () -> Unit = { }) {

    val viewModel = koinViewModel<HomeViewModel>()
    val homeState by viewModel.state.collectAsState()

    when (homeState) {
        is HomeState.Content -> {
            Content(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                state = homeState as HomeState.Content,
                onLogoutClick = viewModel::logout,
                onItemClick = viewModel::openVideoByExternalApp
            )
        }
        HomeState.Loading -> {
            // TODO remove box when https://github.com/androidx/constraintlayout/issues/739 is fixed
            Box(modifier = Modifier.fillMaxSize()) {
                LinearIndeterminateProgressIndicator(Modifier.fillMaxWidth())
            }
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
    onItemClick: (UUID) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (logoutButton, content) = createRefs()

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
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }, verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Locations",
                style = OrbitTheme.typography.title3
            )
            state.locations.forEach {
                ListChoice(onClick = { onItemClick(it.id) }) {
                    Text(text = it.name)
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Latest content",
                style = OrbitTheme.typography.title3
            )
            state.recentFiles.forEach {
                ListChoice(onClick = { onItemClick(it.id) }) {
                    Text(text = it.name)
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Continue watching",
                style = OrbitTheme.typography.title3
            )
            state.resumableContent.forEach {
                ListChoice(onClick = { onItemClick(it.id) }) {
                    Text(text = it.name)
                }
            }
        }
    }
}