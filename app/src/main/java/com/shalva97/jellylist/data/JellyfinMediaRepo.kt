package com.shalva97.jellylist.data

import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.userLibraryApi
import org.jellyfin.sdk.api.client.extensions.videosApi
import org.jellyfin.sdk.model.api.BaseItemDto
import java.util.*

class JellyfinMediaRepo constructor(
    private val apiClient: ApiClient,
) {

    suspend fun getItemInfo(itemId: UUID): String {
        val item = apiClient.userLibraryApi.getItem(itemId = itemId)
        return apiClient.videosApi.getVideoStreamUrl(
            itemId,
            mediaSourceId = item.content.mediaSources!!.first().id, static = true
        )
    }

    suspend fun latestMovies(): List<BaseItemDto> {
        val movies = apiClient.userLibraryApi.getLatestMedia()

        return movies.content
    }
}