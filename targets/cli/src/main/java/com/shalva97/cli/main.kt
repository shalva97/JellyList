package com.shalva97.cli

import di.JellyFinApplication
import di.jellyFinModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

// TODO add CLI interface
fun main() = runBlocking {

    startKoin {
        modules(jellyFinModule)
    }

    val servers = JellyFinApplication().jellyFin.discoverServers()

    servers.forEach {
        println(it)
    }

    println("hello cli client")
}