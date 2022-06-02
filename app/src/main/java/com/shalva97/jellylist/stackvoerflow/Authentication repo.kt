package com.shalva97.jellylist.stackvoerflow

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepoWithInject @Inject constructor(
    @ApplicationContext appContext: Context,
) {

    fun login(): Response {
        TODO() // some stuff
    }

    suspend fun connect(url: String) {
        // blah
    }
}