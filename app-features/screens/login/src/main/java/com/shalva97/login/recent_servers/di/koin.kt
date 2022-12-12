package com.shalva97.login.recent_servers.di

import com.shalva97.core.jellyfinClient
import com.shalva97.login.recent_servers.data.recentServerDataStoreModule
import com.shalva97.login.recent_servers.presentation.RecentServersViewModel
import di.jellyFinModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recentServersModule = module {
    includes(jellyFinModule, jellyfinClient, recentServerDataStoreModule)
    viewModelOf(::RecentServersViewModel)
}