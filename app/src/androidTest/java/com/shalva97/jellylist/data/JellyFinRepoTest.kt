package com.shalva97.jellylist.data

import androidx.test.core.app.launchActivity
import com.shalva97.jellylist.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.jellyfin.sdk.model.api.ServerDiscoveryInfo
import org.junit.Test

@HiltAndroidTest
class JellyFinRepoTest {

    @BindValue
    val jellyFinClient: JellyFinRepo = mockk()

    @Test
    fun bla2h() {

        every { jellyFinClient.discoverServers() } returns
                flowOf(ServerDiscoveryInfo(address = "blah.com"))
        val activity = launchActivity<MainActivity>()



        Thread.sleep(100000)
    }
}
