package di

import data.JellyFinRepo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class JellyFinApplication : KoinComponent {
    val jellyFin by inject<JellyFinRepo>()
}