package com.example.recent_servers

import androidx.datastore.core.DataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.recent_servers.data.recentServerDataStoreModule
import com.shalva97.recent_servers.RecentServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {

    @Before
    fun setUp() {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(recentServerDataStoreModule)
        }
    }

    @Test
    fun useAppContext() {
        val ds: DataStore<Set<RecentServer>> by inject()

        runBlocking {
            ds.updateData { emptySet() }

            ds.updateData {
                it + listOf(RecentServer(
                    name = "Arch Linux",
                    address = "123.123.123.123"
                ))
            }
        }

        runBlocking {
            val first = ds.data.first()
            val data = first.first().name == "Arch Linux"
            assert(data)
        }
    }
}

