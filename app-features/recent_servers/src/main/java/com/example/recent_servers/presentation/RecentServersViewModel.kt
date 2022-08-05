package com.example.recent_servers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.JellyFinRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.JellyFinServer

class RecentServersViewModel(
    private val jellyFinRepo: JellyFinRepo,
) : ViewModel() {

    val discoveredServers = jellyFinRepo.discoveredServers

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jellyFinRepo.discover()
        }
    }
}
