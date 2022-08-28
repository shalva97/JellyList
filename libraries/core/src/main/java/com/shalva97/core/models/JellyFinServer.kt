package com.shalva97.core.models

import kotlinx.serialization.Serializable
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo

@Serializable
data class JellyFinServer(
    val address: String,
    val discoveryType: JellyFinServerType = JellyFinServerType.DISCOVERED,
    val name: String
) {
    constructor(serverDiscoveryInfo: RecommendedServerInfo) : this(
        address = serverDiscoveryInfo.address,
        name = serverDiscoveryInfo.systemInfo.getOrNull()?.serverName ?: ""
    )

    constructor(serverDiscoveryInfo: ServerDiscoveryInfo) : this(
        serverDiscoveryInfo.address,
        name = serverDiscoveryInfo.name
    )
}

enum class JellyFinServerType(val description: String) {
    DISCOVERED("Discovered"),
    RECENT("Recent"),
    RECENT_AND_DISCOVERED("Recent and discovered")
}