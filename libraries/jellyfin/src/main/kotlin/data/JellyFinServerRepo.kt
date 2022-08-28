package data

import androidx.datastore.core.DataStore
import com.shalva97.core.models.JellyFinServer
import kotlinx.coroutines.flow.*
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.discovery.RecommendedServerInfoScore
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo

class JellyFinServerRepo constructor(
    private val jellyFin: Jellyfin,
    private val recentServersStore: DataStore<Set<JellyFinServer>>,
) {

    val servers = MutableStateFlow<Set<JellyFinServer>>(emptySet())

    suspend fun discoverServers(): Set<JellyFinServer> {
        return jellyFin.discovery.discoverLocalServers()
            .map { it.toDomainModel() }
            .toSet(mutableSetOf())
    }

    suspend fun discover() {
        val value = discoverServers() + recentServersStore.data.first()
        servers.tryEmit(value)
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