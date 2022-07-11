package com.shalva97.jellylist.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinApiClientRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jellyfin.sdk.model.api.BaseItemDto

class HomeViewModel(
    private val jellyFinApiClient: JellyFinApiClientRepo,
    private val context: Context,
) : ViewModel() {

    val movies = MutableStateFlow<List<BaseItemDto>>(emptyList())
    val navigateToLogin = Channel<Unit>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is IllegalArgumentException -> {
                runBlocking { delay(500) }
                navigateToLogin.trySend(Unit)
            }
            else -> exception.printStackTrace()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            jellyFinApiClient.initialize()
            movies.emit(jellyFinApiClient.latestMovies())
        }
    }

    fun openVideoByExternalApp(video: BaseItemDto) = viewModelScope.launch(Dispatchers.IO) {
        val streamUrl = jellyFinApiClient.getItemInfo(video.id)

        val uri = Uri.parse(streamUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, "audio/video")
        context.startActivity(intent)
    }
}