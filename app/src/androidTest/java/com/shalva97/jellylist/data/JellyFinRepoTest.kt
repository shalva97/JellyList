package com.shalva97.jellylist.data

import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

class JellyFinRepoTest {

    @Test
    fun blah() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        assert(id.isNotEmpty())
    }
}