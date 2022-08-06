package com.shalva97.jellylist.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinApiClientRepo
import com.shalva97.recent_servers.RecentServer
import data.JellyFinRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import models.JellyFinServer

class LoginScreenViewModel constructor(
    private val jellyFinClient: JellyFinRepo,
    private val jellyFinApiClient: JellyFinApiClientRepo,
    private val recentServersRepo: DataStore<RecentServer>,
) : ViewModel() {

    //    val foundServers = jellyFinClient.discoverServers()
//        .runningFold(emptyList<JellyFinServer>()) { accumulator, value -> accumulator + value }
//        .flowOn(Dispatchers.IO)
    val foundServers = flowOf(emptyList<JellyFinServer>()) // TODO

    //    val previousServers = recentServersRepo.servers
//    val previousServers = recentServersRepo.servers
    val server = mutableStateOf("192.168.")
    val errors = mutableStateOf<Errors>(Errors.NoErrors)
    val showAuthFields = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val authDetails = AuthDetails()
    val navigateToHome = Channel<Unit>()

    fun clearError() {
        errors.value = Errors.NoErrors
    }

    fun onNextButtonClicked() {
        if (showAuthFields.value) {
            authenticate()
        } else {
            connectToServer(server.value)
        }
    }

    private fun authenticate() = viewModelScope.launch(exceptionHandler) {
        jellyFinApiClient.authenticate(
            username = authDetails.username.value,
            password = authDetails.password.value,
        )
        navigateToHome.trySend(Unit)
    }

    fun serverTrailingIconClicked() {
        if (showAuthFields.value.not()) {
            server.value = ""
            clearError()
        }
    }

    private fun connectToServer(jellyFinServer: String) = viewModelScope.launch(exceptionHandler) {
        val recommendedServer = jellyFinClient.findRecommendedServer(jellyFinServer)
        jellyFinApiClient.baseUrl = recommendedServer.address
        showAuthFields.value = true
//        recentServersRepo.saveServer(recommendedServer.address)
    }

    fun onRecentServerClicked(url: String) {
        server.value = url
        connectToServer(url)
    }

    fun onDiscoveredServerClicked(jellyFinServer: JellyFinServer) {
        server.value = jellyFinServer.address
        connectToServer(jellyFinServer.address)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        errors.value = Errors.BadServer
    }
}

data class AuthDetails(
    var username: MutableState<String> = mutableStateOf("shalva"),
    var password: MutableState<String> = mutableStateOf("shalva"),
)

sealed class Errors {
    object BadServer : Errors()
    object NoErrors : Errors()
}