package com.shalva97.cli

import di.JellyFinApplication
import di.jellyFinModule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

fun main() = runBlocking {

    startKoin {
        modules(jellyFinModule)
    }

    JellyFinApplication().jellyFin.discoverServers()
        .collect {
            it
        }

    println("hello cli client")
}