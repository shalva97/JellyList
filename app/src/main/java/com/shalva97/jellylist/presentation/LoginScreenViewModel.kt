package com.shalva97.jellylist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jellyfin.sdk.Jellyfin
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: Jellyfin,
) : ViewModel() {



    init {
        jellyFinClient.discovery.discoverLocalServers(timeout = 2)
            .onEach {
                it
            }.launchIn(viewModelScope)
    }

}