package com.shalva97.jellylist.data

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JellyFinRepo @Inject constructor(
    private val jellyFin: Jellyfin,
) {

    private val jellyFinAPIClient by lazy {
        jellyFin.createApi()
    }

    fun discoverServers(): Flow<ServerDiscoveryInfo> {
        return jellyFin.discovery.discoverLocalServers()
    }

    suspend fun connect(url: String) {
        val server = findRecommendedServer(url)

    }

    @VisibleForTesting
    suspend fun findRecommendedServer(url: String): List<RecommendedServerInfo> {
        return jellyFin.discovery.getRecommendedServers(url)
            .toList().sortedBy { it.score.ordinal }
    }
}
