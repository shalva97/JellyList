package com.shalva97.jellylist.presentation.home

import androidx.lifecycle.ViewModel
import com.shalva97.jellylist.data.JellyFinApiClientRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jellyFinApiClient: JellyFinApiClientRepo,
) : ViewModel() {


    val movies = jellyFinApiClient
}