@file:OptIn(ExperimentalSerializationApi::class)

package com.shalva97.recent_servers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.shalva97.core.JellyFinServer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

object JellyFinServerSerializer : Serializer<Set<JellyFinServer>> {
    override val defaultValue: Set<JellyFinServer> = emptySet()

    override suspend fun readFrom(input: InputStream): Set<JellyFinServer> {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Set<JellyFinServer>,
        output: OutputStream,
    ) {
        val encodeToByteArray = ProtoBuf.encodeToByteArray(t)
        output.write(encodeToByteArray)
    }
}

const val RECENT_SERVERS_FILE_NAME = "settings.pb"
