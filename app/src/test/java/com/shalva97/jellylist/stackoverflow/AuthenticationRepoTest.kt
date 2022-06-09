package com.shalva97.jellylist.stackoverflow

import com.shalva97.jellylist.stackvoerflow.AuthModule
import com.shalva97.jellylist.stackvoerflow.AuthenticationRepo
import com.shalva97.jellylist.stackvoerflow.Response
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AuthModule::class)
class AuthenticationRepoTest {

    @BindValue
    val authRepo: AuthenticationRepo = mockk()

    @Test
    fun logInTest() {

        every { authRepo.login() } returns Response(true)

        assert(authRepo.login().isLoggedIn)

    }
}