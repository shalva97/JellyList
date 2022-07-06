package com.shalva97.jellylist.data

import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Test

@HiltAndroidTest
class JellyFinRepoTest {

    @BindValue
    val jellyFinClient: JellyFinRepo = mockk()

    @Test
    fun bla2h() {



        Thread.sleep(100000)
    }
}
