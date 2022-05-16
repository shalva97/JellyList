package com.shalva97.jellylist.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.jellyfin.sdk.model.DeviceInfo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JellyListModule {

    @Provides
    @Singleton
    fun jellyFinClient(@ApplicationContext appContext: Context): Jellyfin {
        return createJellyfin {
            clientInfo = ClientInfo("JellyList", "1.33.7")
            deviceInfo = DeviceInfo("123", "Xuiaomi")
            context = appContext
        }
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}