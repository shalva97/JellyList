package com.example.recent_servers

import androidx.datastore.core.DataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.recent_servers.data.recentServerDataStoreModule
import com.shalva97.core.models.JellyFinServer
import com.shalva97.core.models.JellyFinServerType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Ignore

@RunWith(AndroidJUnit4::class)
@Ignore("A learning test")
class DatastoreLearningTest : KoinTest {

    @Before
    fun setUp() {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(recentServerDataStoreModule)
        }
        val ds: DataStore<Set<JellyFinServer>> by inject()
        runBlocking { ds.updateData { emptySet() } }
    }

    @Test
    fun savingJellyfinServers() {
        val ds: DataStore<Set<JellyFinServer>> by inject()

        runBlocking {
            ds.updateData { emptySet() }

            ds.updateData {
                it + listOf(
                    JellyFinServer(
                        address = "123.123.123.123",
                        discoveryType = JellyFinServerType.DISCOVERED,
                        name = "blah"
                    )
                )
            }
        }

        runBlocking {
            val first = ds.data.first()
            val data = first.first().name == "blah"
            assert(data)
        }
    }

    @Test
    fun dataStoreIsEmpty() {
        val ds: DataStore<Set<JellyFinServer>> by inject()

        runBlocking {
            val first = ds.data.first()
            val data = first.isEmpty()
            assert(data)
        }
    }
}

