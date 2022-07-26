package com.example.recent_servers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.JellyFinRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.JellyFinServer

class RecentServersViewModel(
    private val recentServersRepo: JellyFinRepo,
) : ViewModel() {

    val discoveredServers = MutableStateFlow<List<JellyFinServer>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val servers = recentServersRepo.discoverServers()
            discoveredServers.emit(servers)
        }
    }
}
