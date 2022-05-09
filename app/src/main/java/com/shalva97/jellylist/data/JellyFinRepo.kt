package com.shalva97.jellylist.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JellyFinRepo @Inject constructor(
    private val jellyFin: Jellyfin,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun discoverServers(): Flow<ServerDiscoveryInfo> = withContext(ioDispatcher) {
        jellyFin.discovery.discoverLocalServers()
    }
}