package com.shalva97.jellylist.data

import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.userApi
import org.jellyfin.sdk.model.api.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JellyFinApiClientRepo @Inject constructor(
    private val apiClient: ApiClient,
) {
    var baseUrl: String? = null
        get() = apiClient.baseUrl
        set(value) {
            apiClient.baseUrl = value
            field = value
        }

    suspend fun fetchListOfAvailableUsers(): Response<List<UserDto>> {
        return apiClient.userApi.getUsers()
    }

    suspend fun authenticate(password: String, username: String) {
        apiClient.userApi.authenticateUser(TODO(), username)
    }
}