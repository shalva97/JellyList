package models

import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo

data class JellyFinServer(val address: String) {
    constructor(serverDiscoveryInfo: RecommendedServerInfo) : this(
        address = serverDiscoveryInfo.address
    )

    constructor(serverDiscoveryInfo: ServerDiscoveryInfo) : this(serverDiscoveryInfo.address)
}