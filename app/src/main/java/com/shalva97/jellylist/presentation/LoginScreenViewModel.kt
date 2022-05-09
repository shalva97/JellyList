package com.shalva97.jellylist.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jellyfin.sdk.Jellyfin
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val jellyFinClient: Jellyfin,
) : ViewModel() {

    init {
        jellyFinClient.clientInfo
    }

}