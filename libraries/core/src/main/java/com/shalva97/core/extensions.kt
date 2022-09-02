package com.shalva97.core

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

fun Job.withLoader(state: MutableStateFlow<Boolean>) {
    state.tryEmit(true)
    invokeOnCompletion { state.tryEmit(false) }
}
