package com.shalva97.cli

import com.shalva97.core.models.JellyFinServer
import data.JellyFinRepo
import di.jellyFinModule
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "JellyList",
    mixinStandardHelpOptions = true,
    version = ["0.0.1"],
    description = ["Command line interface for Jellyfin"]
)
class CLI : Callable<Int>, KoinComponent {

    private val jellyFinRepo: JellyFinRepo by inject()

    @CommandLine.Option(names = ["-d", "--discover"], description = ["Discover servers"])
    var discover = false

    override fun call(): Int {

        if (discover) {
            val servers: Set<JellyFinServer>

            runBlocking {
                servers = jellyFinRepo.discoverServers()
            }

            servers.forEach {
                println(it)
            }

            if (servers.isEmpty())
                println("no servers found")
            println("hello cli client")
        }

        return 0
    }
}

fun main(args: Array<String>) {
    startKoin {
//        printLogger(Level.INFO)
        modules(jellyfinClient, jellyFinModule)
    }
    exitProcess(CommandLine(CLI()).execute(*args))
}
