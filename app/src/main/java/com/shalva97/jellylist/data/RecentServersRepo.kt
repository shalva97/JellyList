package com.shalva97.jellylist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class RecentServersRepo @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    val servers = context.dataStore.data.mapNotNull { it[KNOWN_SERVER] }

    suspend fun saveServer(url: String) {
        context.dataStore.edit {
            it[KNOWN_SERVER] = (it[KNOWN_SERVER] ?: emptySet()) + setOf(url)
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "servers")
private val KNOWN_SERVER = stringSetPreferencesKey("known_servers")