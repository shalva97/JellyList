package com.shalva97.recent_servers

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val blah = loadKoinModules(module {
    single { SettingsSerializer }
})
