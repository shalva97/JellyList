package com.shalva97.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.JellyFinAuthRepo
import data.JellyfinMediaRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jellyfin.sdk.model.api.BaseItemDto

class HomeViewModel(
    private val jellyfinMediaRepo: JellyfinMediaRepo,
    private val authRepo: JellyFinAuthRepo,
    private val context: Context,
) : ViewModel() {

    val movies = MutableStateFlow<List<BaseItemDto>>(emptyList())
    val navigateToLogin = Channel<Unit>(capacity = 1)
    private val exceptionHandler = createExceptionHandler()

    fun initialize() {
        viewModelScope.launch(exceptionHandler) {
            authRepo.loadUserData()
            movies.emit(jellyfinMediaRepo.latestMovies())
        }
    }

    fun openVideoByExternalApp(video: BaseItemDto) = viewModelScope.launch(Dispatchers.IO) {
        val streamUrl = jellyfinMediaRepo.getItemInfo(video.id)
        val uri = Uri.parse(streamUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, "audio/video")
        context.startActivity(intent)
    }

    private fun createExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            when (exception) {
                is IllegalStateException -> {
                    navigateToLogin.trySend(Unit)
                }
                else -> exception.printStackTrace()
            }
        }
    }

    fun logout() = viewModelScope.launch {
        authRepo.clearUserData()
        navigateToLogin.trySend(Unit)
    }
}