package com.shalva97.jellylist

import com.shalva97.jellylist.data.JellyFinRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class ExampleUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var jellyFin: JellyFinRepo

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun jellyFin() = runBlocking {
        jellyFin.discoverServers().collect {
            println(it.address)
        }
        delay(5000)
    }
}