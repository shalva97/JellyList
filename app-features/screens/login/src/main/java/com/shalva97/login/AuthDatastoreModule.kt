package com.shalva97.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.shalva97.core.models.LogInState
import com.shalva97.serializers.USER_DATA
import com.shalva97.serializers.createProtobufSerializer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userDataDatastoreModule = module {
    single(named(USER_DATA)) { get<Context>().userData }
}

private val Context.userData: DataStore<LogInState> by dataStore(
    fileName = USER_DATA,
    serializer = createProtobufSerializer(LogInState.NotLoggedIn)
)