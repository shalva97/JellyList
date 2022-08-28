package com.shalva97.login

import androidx.datastore.core.DataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shalva97.core.models.LogInState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
@Ignore("A learning test")
class DatastoreLearningTest : KoinTest {

    init {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(userDataDatastoreModule)
        }
    }

    @Before
    fun setUp() {
        val ds: DataStore<LogInState> by inject()
        runBlocking { ds.updateData { LogInState.NotLoggedIn } }
    }

    @Test
    fun userIsLoggedOut() {
        val ds: DataStore<LogInState> by inject()

        runBlocking {
            val userData = ds.data.first()
            assert(userData is LogInState.NotLoggedIn)
        }
    }

    @Test
    fun loggedInUser() {
        val ds: DataStore<LogInState> by inject()

        runBlocking {
            val userData = ds.updateData {
                LogInState.UserData("shalva", "qwerty", "http://some.web")
            }
            assert(userData is LogInState.UserData)
            assert((userData as LogInState.UserData).userName == "shalva")
            val userData1 = ds.updateData {
                LogInState.NotLoggedIn
            }
            assert(userData1 is LogInState.NotLoggedIn)
        }
    }
}