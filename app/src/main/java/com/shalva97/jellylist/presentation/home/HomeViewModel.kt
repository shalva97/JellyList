package com.shalva97.jellylist.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyfinMediaRepo
import data.JellyFinAuthRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jellyfin.sdk.model.api.BaseItemDto

class HomeViewModel(
    private val jellyfinMediaRepo: JellyfinMediaRepo,
    private val authRepo: JellyFinAuthRepo,
    private val context: Context,
) : ViewModel() {

    val movies = MutableStateFlow<List<BaseItemDto>>(emptyList())
    val navigateToLogin = Channel<Unit>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is IllegalStateException -> {
                runBlocking { delay(500) }
                navigateToLogin.trySend(Unit)
            }
            else -> exception.printStackTrace()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            authRepo.loadUserData()
            movies.emit(jellyfinMediaRepo.latestMovies())
        }
    }

    fun openVideoByExternalApp(video: BaseItemDto) = viewModelScope.launch(Dispatchers.IO) {
        val streamUrl = jellyfinMediaRepo.getItemInfo(video.id)
//
        val uri = Uri.parse(streamUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, "audio/video")
        context.startActivity(intent)
    }
}