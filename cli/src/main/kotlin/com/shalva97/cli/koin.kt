package com.shalva97.cli

import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.dsl.module

val jellyfinClient = module {
    single {
        createJellyfin {
            clientInfo = ClientInfo("JellyList", "0.1")
        }
    }
}
