package di

import com.shalva97.recent_servers.RECENT_SERVERS
import data.JellyFinAuthRepo
import data.JellyFinServerRepo
import data.USER_DATA
import org.koin.core.qualifier.named
import org.koin.dsl.module

val jellyFinModule = module {
    single { JellyFinServerRepo(get(), get(named(RECENT_SERVERS))) }
    single { JellyFinAuthRepo(get(), get(named(USER_DATA))) }
}