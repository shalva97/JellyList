@file:OptIn(ExperimentalSerializationApi::class)

package com.shalva97.recent_servers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Set<RecentServer>> {
    override val defaultValue: Set<RecentServer> = emptySet()

    override suspend fun readFrom(input: InputStream): Set<RecentServer> {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Set<RecentServer>,
        output: OutputStream,
    ) {
        val encodeToByteArray = ProtoBuf.encodeToByteArray(t)
        output.write(encodeToByteArray)
    }
}

const val RECENT_SERVERS_FILE_NAME = "settings.pb"

@Serializable
data class RecentServer(
    val name: String = "2",
    val address: String = "2",
)