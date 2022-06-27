package com.shalva97.jellylist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shalva97.jellylist.data.JellyFinApiClientRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.jellyfin.sdk.model.api.BaseItemDto
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jellyFinApiClient: JellyFinApiClientRepo,
) : ViewModel() {

    val movies = MutableSharedFlow<List<BaseItemDto>>()
    val navigateToLogin = Channel<Unit>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is IllegalArgumentException -> {
                navigateToLogin.trySend(Unit)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            jellyFinApiClient.initialize()
            movies.emit(jellyFinApiClient.latestMovies())
        }
    }

}