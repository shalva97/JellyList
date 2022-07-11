package data

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import models.JellyFinServer
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.discovery.RecommendedServerInfoScore
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo

class JellyFinRepo constructor(
    private val jellyFin: Jellyfin,
) {

    suspend fun discoverServers(): List<JellyFinServer> {
        return jellyFin.discovery.discoverLocalServers().map { it.toDomainModel() }
            .toList(mutableListOf())
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