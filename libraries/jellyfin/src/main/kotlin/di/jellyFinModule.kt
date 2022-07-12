package di

import data.JellyFinRepo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val jellyFinModule = module {
    singleOf(::JellyFinRepo)
}