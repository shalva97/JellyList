package com.shalva97.jellylist.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo

@Module
@InstallIn(SingletonComponent::class)
class SingletonComponents {

    @Provides
    fun jellyFinClient(@ApplicationContext appContext: Context): Jellyfin {
        return createJellyfin {
            clientInfo = ClientInfo("JellyList", "1.33.7")
            context = appContext
        }
    }
}