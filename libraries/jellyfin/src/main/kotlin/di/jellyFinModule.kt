package di

import data.JellyFinRepo
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val jellyFinModule = module {
    single {
        createJellyfin {
            clientInfo = ClientInfo("JellyList", "0.1")
        }
    }
    singleOf(::JellyFinRepo)
}