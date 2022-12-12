package com.shalva97.login.recent_servers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.shalva97.core.models.JellyFinServer
import com.shalva97.serializers.JellyFinServerSerializer
import com.shalva97.serializers.RECENT_SERVERS
import org.koin.core.qualifier.named
import org.koin.dsl.module

val recentServerDataStoreModule = module {
    single(named(RECENT_SERVERS)) { get<Context>().recentServerDataStore }
}

private val Context.recentServerDataStore: DataStore<Set<JellyFinServer>> by dataStore(
    fileName = RECENT_SERVERS,
    serializer = JellyFinServerSerializer
)