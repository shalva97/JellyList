package com.shalva97.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import data.USER_DATA_FILE
import data.UserDataSerializer
import models.LogInState
import org.koin.dsl.module

val userDataDatastoreModule = module {
    single { get<Context>().userData }
}

private val Context.userData: DataStore<LogInState> by dataStore(
    fileName = USER_DATA_FILE,
    serializer = UserDataSerializer
)