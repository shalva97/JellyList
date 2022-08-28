package data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import models.LogInState
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.authenticateUserByName
import org.jellyfin.sdk.api.client.extensions.userApi
import java.util.*

class JellyFinAuthRepo constructor(
    private val apiClient: ApiClient,
    private val loginDataStore: DataStore<LogInState>
) {
    var baseUrl: String? = null
        get() = apiClient.baseUrl
        set(value) {
            apiClient.baseUrl = value
            field = value
        }

    suspend fun loadUserData() {
        val state = loginDataStore.data.first()
        if (state is LogInState.UserData) {
            apiClient.accessToken = state.token
            apiClient.userId = UUID.fromString(state.userName)
            apiClient.baseUrl = state.baseUrl
        } else throw IllegalStateException("No auth token saved to device")
    }

    suspend fun authenticate(password: String, username: String) {
        val authDetails = apiClient.userApi.authenticateUserByName(username, password)
        apiClient.accessToken = authDetails.content.accessToken
        apiClient.userId = authDetails.content.user?.id

        loginDataStore.updateData {
            LogInState.UserData(
                authDetails.content.user?.id?.toString()
                    ?: return@updateData LogInState.NotLoggedIn,
                authDetails.content.accessToken ?: return@updateData LogInState.NotLoggedIn,
                baseUrl ?: return@updateData LogInState.NotLoggedIn,
            )
        }
    }

}
