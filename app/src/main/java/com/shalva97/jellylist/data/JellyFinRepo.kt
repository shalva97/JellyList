package com.shalva97.jellylist.data

import com.shalva97.jellylist.domain.JellyFinServer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.discovery.RecommendedServerInfoScore
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

    fun discoverServers(): Flow<JellyFinServer> {
        return jellyFin.discovery.discoverLocalServers().map { it.toDomainModel() }
    }

    suspend fun connect(url: String): RecommendedServerInfo {
        val server = findRecommendedServer(url)


        return TODO()
    }

    suspend fun findRecommendedServer(url: String): JellyFinServer {
        val candidates = jellyFin.discovery.getAddressCandidates(url)
        val server = jellyFin.discovery.getRecommendedServers(candidates)
            .toList().sortedBy { it.score.ordinal }

        if (server.all { it.score == RecommendedServerInfoScore.BAD }) {
            throw Exception("No Server found")
        } else {
            return server.first().toDomainModel()
        }
    }
}

private fun RecommendedServerInfo.toDomainModel(): JellyFinServer {
   return JellyFinServer(this)
}

private fun ServerDiscoveryInfo.toDomainModel(): JellyFinServer {
    return JellyFinServer(this)
}