package com.example.recent_servers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.shalva97.recent_servers.SETTINGS_FILE_NAME
import com.shalva97.recent_servers.Settings
import com.shalva97.recent_servers.SettingsSerializer
import org.koin.dsl.module

val settingsDataStoreModule = module {
    single { get<Context>().settingsDataStore }
}

private val Context.settingsDataStore: DataStore<Settings> by dataStore(
    fileName = SETTINGS_FILE_NAME,
    serializer = SettingsSerializer
)