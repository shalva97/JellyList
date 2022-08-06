package com.example.recent_servers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.JellyFinRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentServersViewModel(
    private val jellyFinRepo: JellyFinRepo,
) : ViewModel() {

    val servers = jellyFinRepo.servers

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jellyFinRepo.discover()
        }
    }
}
