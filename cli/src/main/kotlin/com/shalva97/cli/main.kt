package com.shalva97.cli

import com.shalva97.core.models.JellyFinServer
import data.JellyFinServerRepo
import di.jellyFinModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    val koin = startKoin {
//        printLogger(Level.INFO)
        modules(jellyfinClient, jellyFinModule)
    }.koin

    val servers: Set<JellyFinServer>

    val jellyFinRepo: JellyFinServerRepo = koin.get()

    runBlocking {
        servers = jellyFinRepo.discoverServers()
    }

    servers.forEach {
        println(it)
    }

    if (servers.isEmpty()) println("no servers found")
}
