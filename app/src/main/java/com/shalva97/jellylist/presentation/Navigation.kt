package com.shalva97.jellylist.presentation

import kotlinx.coroutines.flow.MutableSharedFlow

val navigation = MutableSharedFlow<String>(extraBufferCapacity = 1)
