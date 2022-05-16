package com.shalva97.jellylist.data

import com.shalva97.jellylist.data.di.JellyListModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.discovery.RecommendedServerInfo
import org.jellyfin.sdk.discovery.RecommendedServerInfoScore
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@UninstallModules(JellyListModule::class)
class JellyFinRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val jellyFin: Jellyfin = mockk()

    @Inject
    lateinit var jellyFinRepo: JellyFinRepo

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun findRecommendedServer_isSortedByGREATServerFirst(): Unit = runBlocking {

        val serversWithSpecificScores = getRandomServers()

        coEvery {
            jellyFin.discovery.getRecommendedServers(any<String>())
        } returns flowOf(*serversWithSpecificScores)

        val servers = jellyFinRepo.findRecommendedServer("192.168.88.206")

        assert(servers.first().score == RecommendedServerInfoScore.GREAT)
    }
}

private fun getRandomServers(): Array<RecommendedServerInfo> {
    return Array(5) {
        mockk<RecommendedServerInfo> {
            every { score } returns RecommendedServerInfoScore.values().random()
        }
    } + mockk<RecommendedServerInfo> {
        every { score } returns RecommendedServerInfoScore.GREAT
    }
}