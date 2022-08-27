package com.example.recent_servers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.shalva97.core.models.JellyFinServer
import com.shalva97.recent_servers.JellyFinServerSerializer
import com.shalva97.recent_servers.RECENT_SERVERS_FILE_NAME
import org.koin.dsl.module

val recentServerDataStoreModule = module {
    single { get<Context>().recentServerDataStore }
}

private val Context.recentServerDataStore: DataStore<Set<JellyFinServer>> by dataStore(
    fileName = RECENT_SERVERS_FILE_NAME,
    serializer = JellyFinServerSerializer
)