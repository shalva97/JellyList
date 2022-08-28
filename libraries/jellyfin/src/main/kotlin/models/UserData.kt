package models

import kotlinx.serialization.Serializable

@Serializable
sealed class LogInState {
    @Serializable
    data class UserData(val userName: String, val token: String, val baseUrl: String) : LogInState()

    @Serializable
    object NotLoggedIn : LogInState()
}