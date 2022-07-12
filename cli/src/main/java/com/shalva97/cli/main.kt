package com.shalva97.cli

import di.jellyFinModule
import kotlinx.coroutines.runBlocking
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.koin.core.context.startKoin
import org.koin.dsl.module

// TODO add CLI interface
fun main() = runBlocking {

    startKoin {
        module {
            single {
                createJellyfin {
                    clientInfo = ClientInfo("JellyList", "0.1")
                }
            }
        }
        modules(jellyFinModule)
    }

//    val servers = JellyFinApplication().jellyFin.discoverServers()
//
//    servers.forEach {
//        println(it)
//    }

    println("hello cli client")
}