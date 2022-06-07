package com.shalva97.jellylist.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinRepo
import com.shalva97.jellylist.domain.JellyFinServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: JellyFinRepo,
) : ViewModel() {

    val foundServers = jellyFinClient.discoverServers()
        .runningFold(emptyList<JellyFinServer>()) { accumulator, value -> accumulator + value }
        .flowOn(Dispatchers.IO)
    val server = mutableStateOf("192.168.")
    val errors = mutableStateOf<Errors>(Errors.NoErrors)
    val loading = mutableStateOf(false)

    fun connectToServer(jellyFinServer: JellyFinServer) =
        viewModelScope.launch(exceptionHandler) {
            server.value = jellyFinServer.address
            jellyFinClient.findRecommendedServer(jellyFinServer.address)
        }

    fun clearError() {
        errors.value = Errors.NoErrors
    }

    fun connectToServer() =
        viewModelScope.launch(exceptionHandler) {
            jellyFinClient.findRecommendedServer(server.value)
        }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errors.value = Errors.BadServer
    }
}

sealed class Errors {
    object BadServer: Errors()
    object NoErrors: Errors()
}