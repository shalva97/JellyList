package com.example.recent_servers

import androidx.datastore.core.DataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shalva97.recent_servers.Settings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val ds = startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(settingsDataStoreModule)
        }.koin.get<DataStore<Settings>>()

        runBlocking {
            ds.updateData {
                it.copy("123")
            }
        }


        // read stuff from disk

        runBlocking {
            val data = ds.data.first().name == "123"
            assert(data)
        }
    }
}

