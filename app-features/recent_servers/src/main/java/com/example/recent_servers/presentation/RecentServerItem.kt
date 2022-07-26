package com.example.recent_servers.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.shalva97.core.jellyfinClient
import di.jellyFinModule
import kiwi.orbit.compose.ui.controls.ListChoice
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Composable
fun RecentServers() {

    val viewModel = koinViewModel<RecentServersViewModel>()
    val discoveredServer = viewModel.discoveredServers.collectAsState()

    Column {
        Text(text = "Servers", fontWeight = FontWeight.SemiBold)

        LazyColumn {
            items(discoveredServer.value.size) { index ->
                ListChoice(onClick = {
//                viewModel.onDiscoveredServerClicked(discoveredServer.value[index])
                }, description = {
                    Text(text = "Discovered")
                }) {
                    Text(text = discoveredServer.value[index].address)
                }
            }

//        items(previousServers.value.size) { index ->
//            ListChoice(onClick = {
//                viewModel.onRecentServerClicked(previousServers.value.elementAt(index))
//            }, description = {
//                Text(text = "Recent")
//            }) {
//                Text(text = previousServers.value.elementAt(index))
//            }
//
//        }
        }
    }


}

@Preview
@Composable
fun RecentServerItem() {
    val androidContext = LocalContext.current
    startKoin {
        androidContext(androidContext)
        modules(jellyFinModule, jellyfinClient)

        modules(module {
            viewModelOf(::RecentServersViewModel)
        })
    }
    RecentServers()
}