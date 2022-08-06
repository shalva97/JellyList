package models

import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo

data class JellyFinServer(
    val address: String,
    val discoveryType: JellyFinServerType = JellyFinServerType.DISCOVERED,
) {
    constructor(serverDiscoveryInfo: RecommendedServerInfo) : this(
        address = serverDiscoveryInfo.address
    )

    constructor(serverDiscoveryInfo: ServerDiscoveryInfo) : this(serverDiscoveryInfo.address)
}

enum class JellyFinServerType(val description: String) {
    DISCOVERED("Discovered"), RECENT("Recent"), RECENT_AND_DISCOVERED("Recent and discovered")
}