package com.shalva97.jellylist.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinRepo
import com.shalva97.jellylist.domain.JellyFinServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: JellyFinRepo,
) : ViewModel() {

    val foundServers1 = jellyFinClient.discoverServers()
        .runningFold(emptyList<JellyFinServer>()) { accumulator, value -> accumulator + value }
        .flowOn(Dispatchers.IO)
    val server = mutableStateOf<String>("192.168.")

    val errors = MutableStateFlow<Errors>(Errors.NoErrors)

    val foundServers = flowOf(
        listOf(
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
        ))

    val loading = MutableStateFlow<Boolean>(false)

    fun connectToServer(jellyFinServer: JellyFinServer) =
        viewModelScope.launch(exceptionHandler) {
            server.value = jellyFinServer.address
            jellyFinClient.connect(jellyFinServer.address)
        }

    fun clearError() {
        errors.tryEmit(Errors.NoErrors)
    }

    fun connectToServer() =
        viewModelScope.launch(exceptionHandler) {
            jellyFinClient.connect(server.value)
        }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errors.tryEmit(Errors.BadServer)
    }
}

sealed class Errors {
    object BadServer: Errors()
    object NoErrors: Errors()
}