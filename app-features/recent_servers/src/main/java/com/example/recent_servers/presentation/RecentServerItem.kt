package com.example.recent_servers.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.recent_servers.di.recentServersModule
import com.shalva97.core.JellyFinServer
import com.shalva97.core.JellyFinServerType
import kiwi.orbit.compose.ui.controls.ListChoice
import kiwi.orbit.compose.ui.controls.Scaffold
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
fun RecentServers(
    state: State<List<JellyFinServer>>? = null,
    onServerClick: ((JellyFinServer) -> Unit)? = null,
) {

    val discoveredServer =
        state ?: koinViewModel<RecentServersViewModel>().servers.collectAsState(emptyList())

    if (discoveredServer.value.isNotEmpty())
        Column {
            Text(text = "Servers", fontWeight = FontWeight.SemiBold)

            LazyColumn {
                items(discoveredServer.value.size) { index ->
                    ListChoice(onClick = {
                        onServerClick?.invoke(discoveredServer.value[index])
                    }, description = {
                        Text(text = discoveredServer.value[index].discoveryType.description)
                    }) {
                        Text(text = discoveredServer.value[index].address)
                    }
                }
            }
        }
}

@Preview
@Composable
fun blah() {
    @SuppressLint("UnrememberedMutableState")
    val state = mutableStateOf(listOf(
        JellyFinServer("blah.com", JellyFinServerType.RECENT),
        JellyFinServer("somewebsite.com", JellyFinServerType.RECENT_AND_DISCOVERED),
        JellyFinServer("blah.com"),
        JellyFinServer("blah.com"),
    ))
    Scaffold {
        RecentServers(state = remember { state })
    }
}

@Preview
@Composable
fun RecentServerItem() {
    val androidContext = LocalContext.current

    KoinPlatformTools.defaultContext().getOrNull() ?: startKoin {
        androidContext(androidContext)
        modules(recentServersModule)
    }
    RecentServers()
}