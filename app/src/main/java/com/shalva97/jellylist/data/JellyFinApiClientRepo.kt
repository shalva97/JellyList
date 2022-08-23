package com.shalva97.jellylist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shalva97.jellylist.domain.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.*
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.SortOrder
import org.jellyfin.sdk.model.api.UserDto
import java.util.*

class JellyFinApiClientRepo constructor(
    private val apiClient: ApiClient,
    private val context: Context,
) {
    var baseUrl: String? = null
        get() = apiClient.baseUrl
        set(value) {
            apiClient.baseUrl = value
            field = value
        }

    suspend fun initialize() {
        setLoginCredentials()
    }

    private suspend fun setLoginCredentials() {
        val savedCredentials =
            context.dataStore.data.map { Triple(it[TOKEN], it[USER_ID], it[BASE_URL]) }.first()

        require(savedCredentials.first != null && savedCredentials.second != null)

        apiClient.accessToken = savedCredentials.first
        apiClient.userId = UUID.fromString(savedCredentials.second)
        apiClient.baseUrl = savedCredentials.third
    }

    suspend fun getItemInfo(itemId: UUID): String {
        val item = apiClient.userLibraryApi.getItem(itemId = itemId)
        return apiClient.videosApi.getVideoStreamUrl(itemId,
            mediaSourceId = item.content.mediaSources!!.first().id, static = true)
    }

    suspend fun authenticate(password: String, username: String) {
        val authDetails = apiClient.userApi.authenticateUserByName(username, password)
        apiClient.accessToken = authDetails.content.accessToken
        apiClient.userId = authDetails.content.user?.id
        context.dataStore.edit { prefs ->
            prefs[TOKEN] = authDetails.content.accessToken ?: return@edit
            prefs[USER_ID] = authDetails.content.user?.id?.toString() ?: return@edit
            prefs[BASE_URL] = baseUrl ?: return@edit
        }
    }

    suspend fun latestMovies(): List<BaseItemDto> {
        val movies = apiClient.userLibraryApi.getLatestMedia()

        return movies.content
    }
}

private fun UserDto.toDomainModel(): User {
    return User(name!!, id, hasPassword)
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
private val TOKEN = stringPreferencesKey("TOKEN")
private val USER_ID = stringPreferencesKey("USER_ID")
private val BASE_URL = stringPreferencesKey("BASE_URL")