package com.example.recent_servers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.core.models.JellyFinServer
import data.JellyFinRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecentServersViewModel(
    private val jellyFinRepo: JellyFinRepo,
) : ViewModel() {

    val servers: Flow<List<JellyFinServer>> = jellyFinRepo.servers.map { it.toList() }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jellyFinRepo.discover()
        }
    }
}
