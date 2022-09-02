package com.shalva97.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.core.models.JellyFinServer
import com.shalva97.core.withLoader
import data.JellyFinAuthRepo
import data.JellyFinServerRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel constructor(
    private val jellyFinClient: JellyFinServerRepo,
    private val jellyFinAuthRepo: JellyFinAuthRepo
) : ViewModel() {

    val server = mutableStateOf("192.168.")
    val errors = mutableStateOf<Errors>(Errors.NoErrors)
    val showAuthFields = mutableStateOf(false)
    val loading = MutableStateFlow(false)
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
        jellyFinAuthRepo.authenticate(
            username = authDetails.username.value,
            password = authDetails.password.value,
        )
        navigateToHome.trySend(Unit)
    }.withLoader(loading)

    fun serverTrailingIconClicked() {
        if (showAuthFields.value.not()) {
            server.value = ""
            clearError()
        }
    }

    private fun connectToServer(jellyFinServer: String) = viewModelScope.launch(exceptionHandler) {
        val recommendedServer = jellyFinClient.findRecommendedServer(jellyFinServer)
        jellyFinAuthRepo.baseUrl = recommendedServer.address
        showAuthFields.value = true
    }.withLoader(loading)

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
        loading.value = false
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