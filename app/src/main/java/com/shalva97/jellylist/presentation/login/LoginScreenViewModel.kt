package com.shalva97.jellylist.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinRepo
import com.shalva97.jellylist.domain.JellyFinServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: JellyFinRepo,
) : ViewModel() {

    val foundServers1 = jellyFinClient.discoverServers()
        .runningFold(emptyList<JellyFinServer>()) { accumulator, value -> accumulator + value }
        .flowOn(Dispatchers.IO)

    val errors = MutableStateFlow<Errors>(Errors.NoErrors)

    val foundServers = flowOf(
        listOf(
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
            JellyFinServer("12312 12312 12 3"),
        ))

    val loading = MutableStateFlow<Boolean>(false)

    fun discoveredServers() = viewModelScope.launch(Dispatchers.IO) {
        jellyFinClient.discoverServers()
    }

    fun connectToServer(jellyFinServer: JellyFinServer) =
        viewModelScope.launch(exceptionHandler) {
            jellyFinClient.connect(jellyFinServer.address)
        }

    fun clearError() {
        errors.tryEmit(Errors.NoErrors)
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errors.tryEmit(Errors.BadServer)
    }
}

sealed class Errors {
    object BadServer: Errors()
    object NoErrors: Errors()
}