package data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.collect
import models.LogInState
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.authenticateUserByName
import org.jellyfin.sdk.api.client.extensions.userApi
import java.util.*

// TODO move to another module- done
// remove key/value based presistence
// separate authentication class
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

    suspend fun setLoginCredentials() {
        loginDataStore.data.collect {
            if (it is LogInState.UserData) {
                apiClient.accessToken = it.token
                apiClient.userId = UUID.fromString(it.userName)
                apiClient.baseUrl = it.baseUrl
            }
        }
    }

//    suspend fun getItemInfo(itemId: UUID): String {
//        val item = apiClient.userLibraryApi.getItem(itemId = itemId)
//        return apiClient.videosApi.getVideoStreamUrl(
//            itemId,
//            mediaSourceId = item.content.mediaSources!!.first().id, static = true
//        )
//    }

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
//        context.dataStore.edit { prefs ->
//            prefs[TOKEN] = authDetails.content.accessToken ?: return@edit
//            prefs[USER_ID] = authDetails.content.user?.id?.toString() ?: return@edit
//            prefs[BASE_URL] = baseUrl ?: return@edit
//        }
    }

//    suspend fun latestMovies(): List<BaseItemDto> {
//        val movies = apiClient.userLibraryApi.getLatestMedia()
//
//        return movies.content
//    }
}

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
//private val TOKEN = stringPreferencesKey("TOKEN")
//private val USER_ID = stringPreferencesKey("USER_ID")
//private val BASE_URL = stringPreferencesKey("BASE_URL")