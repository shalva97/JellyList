package di

import com.shalva97.serializers.RECENT_SERVERS
import com.shalva97.serializers.USER_DATA
import data.JellyFinAuthRepo
import data.JellyFinServerRepo
import org.koin.core.qualifier.named
import org.koin.dsl.module

val jellyFinModule = module {
    single { JellyFinServerRepo(get(), get(named(RECENT_SERVERS))) }
    single { JellyFinAuthRepo(get(), get(named(USER_DATA))) }
}