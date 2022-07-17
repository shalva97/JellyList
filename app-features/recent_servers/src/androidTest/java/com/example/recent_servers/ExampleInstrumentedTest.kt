package com.example.recent_servers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shalva97.recent_servers.Settings
import com.shalva97.recent_servers.SettingsSerializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    val Context.settingsDataStore: DataStore<Settings> by dataStore(
        fileName = "settings.pb",
        serializer = SettingsSerializer
    )

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // write stuff to disk
        val ds = DataStoreFactory.create(
            serializer = SettingsSerializer,
            produceFile = {
                appContext.dataStoreFile("settings.pb")
            }
        )

        runBlocking {
            ds.updateData {
                it.toBuilder()
                    .setExampleCounter(4)
                    .build()
            }
        }


        // read stuff from disk

        runBlocking {
            val data = ds.data.first().exampleCounter == 4
            assert(data)
        }
    }
}

