package com.shalva97.jellylist.data

import com.shalva97.jellylist.domain.User
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.authenticateUserByName
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

    suspend fun fetchListOfAvailableUsers(): List<User> {
        return apiClient.userApi.getUsers().content.map { it.toDomainModel() }
    }

    suspend fun authenticate(password: String, username: String) {
        val authenticateUserByName = apiClient.userApi.authenticateUserByName(username, password)
        apiClient.accessToken = authenticateUserByName.content.accessToken
    }
}

private fun UserDto.toDomainModel(): User {
    return User(name!!, id, hasPassword)
}