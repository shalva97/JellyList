package com.shalva97.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.JellyFinAuthRepo
import data.JellyfinMediaRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jellyfin.sdk.api.client.exception.TimeoutException
import org.jellyfin.sdk.model.api.BaseItemDto
import java.util.*

class HomeViewModel(
    private val jellyfinMediaRepo: JellyfinMediaRepo,
    private val authRepo: JellyFinAuthRepo,
    private val context: Context,
) : ViewModel() {

    val state = MutableStateFlow<HomeState>(HomeState.Loading)

    private val exceptionHandler = createExceptionHandler()

    init {
        viewModelScope.launch(exceptionHandler) {
            authRepo.loadUserData()
            state.update {
//                jellyfinMediaRepo.userMedia()
                HomeState.Content(jellyfinMediaRepo.latestMovies().map { it.toUI() })
            }
        }
    }

    fun openVideoByExternalApp(video: Item) = viewModelScope.launch(Dispatchers.IO) {
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
                is IllegalStateException,
                is TimeoutException -> {
                    Log.d("ass", "Exception................")
                    state.update { HomeState.NavigateToLogin }
                }
                else -> exception.printStackTrace()
            }
        }
    }

    fun logout() = viewModelScope.launch {
        authRepo.clearUserData()
        state.update { HomeState.NavigateToLogin }
    }

    fun navigationEventConsumed() {
        state.update { HomeState.Loading }
    }
}

sealed interface HomeState {
    object Loading : HomeState
    object NavigateToLogin : HomeState
    data class Content(
        val movies: List<Item> = emptyList(),
    ) : HomeState
}

data class Item(val name: String, val id: UUID)

private fun BaseItemDto.toUI(): Item {
    return Item(name ?: "Unknown Item", id)
}