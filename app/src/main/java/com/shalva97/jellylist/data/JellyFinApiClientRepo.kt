package com.shalva97.jellylist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shalva97.jellylist.domain.User
import dagger.hilt.android.qualifiers.ApplicationContext
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.authenticateUserByName
import org.jellyfin.sdk.api.client.extensions.userApi
import org.jellyfin.sdk.model.api.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JellyFinApiClientRepo @Inject constructor(
    private val apiClient: ApiClient,
    @ApplicationContext val context: Context,
) {
    var baseUrl: String? = null
        get() = apiClient.baseUrl
        set(value) {
            apiClient.baseUrl = value
            field = value
        }

    suspend fun fetchListOfAvailableUsers(): List<User> {
        return apiClient.userApi.getUsers().content.map { it.toDomainModel() }
    }

    suspend fun authenticate(password: String, username: String) {
        val authenticateUserByName = apiClient.userApi.authenticateUserByName(username, password)
        apiClient.accessToken = authenticateUserByName.content.accessToken
        context.dataStore.edit { prefs ->
            prefs[TOKEN] = authenticateUserByName.content.accessToken ?: return@edit
        }
    }
}

private fun UserDto.toDomainModel(): User {
    return User(name!!, id, hasPassword)
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
private val TOKEN = stringPreferencesKey("jellyfin_token")