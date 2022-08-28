package com.example.recent_servers.di

import com.example.recent_servers.data.recentServerDataStoreModule
import com.example.recent_servers.presentation.RecentServersViewModel
import com.shalva97.core.jellyfinClient
import di.jellyFinModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recentServersModule = module {
    includes(jellyFinModule, jellyfinClient, recentServerDataStoreModule)
    viewModelOf(::RecentServersViewModel)
}