package com.shalva97.jellylist

import android.app.Application
import com.example.recent_servers.di.recentServersModule
import com.shalva97.home.HomeViewModel
import com.shalva97.login.LoginScreenViewModel
import com.shalva97.login.userDataDatastoreModule
import data.JellyfinMediaRepo
import di.jellyFinModule
import kotlinx.coroutines.Dispatchers
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
            modules(jellyFinModule)
            modules(recentServersModule)
            modules(userDataDatastoreModule)
        }
    }
}

val appModule = module {
    single {
        createJellyfin {
            clientInfo = ClientInfo("JellyList", "0.1")
            context = androidContext()
        }
    }
    single { get<Jellyfin>().createApi() }
    singleOf(::JellyfinMediaRepo)
    single { Dispatchers.IO }

    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::HomeViewModel)
}
