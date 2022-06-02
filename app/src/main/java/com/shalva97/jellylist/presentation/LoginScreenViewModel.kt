package com.shalva97.jellylist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: JellyFinRepo,
) : ViewModel() {

//    val foundServers = jellyFinClient.discoverServers()
//        .runningFold(emptyList<ServerDiscoveryInfo>()) { accumulator, value -> accumulator + value }
//        .flowOn(Dispatchers.IO)

    val foundServers = flowOf(
        listOf(
            ServerDiscoveryInfo("12312 12312 12 3"),
            ServerDiscoveryInfo("12312 12312 12 3"),
            ServerDiscoveryInfo("12312 12312 12 3"),
            ServerDiscoveryInfo("12312 12312 12 3"),
        ))

    val loading = MutableStateFlow<Boolean>(false)

    fun discoveredServers() = viewModelScope.launch(Dispatchers.IO) {
        jellyFinClient.discoverServers()
    }

    fun connectToServer(serverDiscoveryInfo: ServerDiscoveryInfo) = viewModelScope.launch {
        jellyFinClient.connect(serverDiscoveryInfo.address ?: return@launch)
    }
}