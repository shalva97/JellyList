package com.shalva97.jellylist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.test.core.app.ApplicationProvider
import com.shalva97.jellylist.App
import com.shalva97.serializers.USER_DATA
import com.shalva97.serializers.createProtobufSerializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test


class DataStoreTest {

    @Test
    fun bla2h() = runBlocking {
        val dataStore = ApplicationProvider.getApplicationContext<App>().userData
        println(dataStore.data.first())
        dataStore.updateData { "zxcv" }
        println(dataStore.data.first())
        println("data")
    }
}


private val Context.userData: DataStore<String> by dataStore(
    fileName = USER_DATA,
    serializer = createProtobufSerializer("123")
)