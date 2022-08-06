package com.example.recent_servers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.shalva97.recent_servers.RECENT_SERVERS_FILE_NAME
import com.shalva97.recent_servers.RecentServer
import com.shalva97.recent_servers.SettingsSerializer
import org.koin.dsl.module

val recentServerDataStoreModule = module {
    single { get<Context>().recentServerDataStore }
}

private val Context.recentServerDataStore: DataStore<Set<RecentServer>> by dataStore(
    fileName = RECENT_SERVERS_FILE_NAME,
    serializer = SettingsSerializer
)