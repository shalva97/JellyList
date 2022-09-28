package data

import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.userLibraryApi
import org.jellyfin.sdk.api.client.extensions.userViewsApi
import org.jellyfin.sdk.api.client.extensions.videosApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult
import java.util.*

class JellyfinMediaRepo(
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

    suspend fun userMedia(): BaseItemDtoQueryResult {
        val mediaItems = apiClient.userViewsApi.getUserViews()
        return mediaItems.content
    }
}