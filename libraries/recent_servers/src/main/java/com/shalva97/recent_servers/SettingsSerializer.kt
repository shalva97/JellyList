package com.shalva97.recent_servers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings = Settings()

    override suspend fun readFrom(input: InputStream): Settings {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: Settings,
        output: OutputStream,
    ) {
        val encodeToByteArray = ProtoBuf.encodeToByteArray(t)
        output.write(encodeToByteArray)
    }
}

const val SETTINGS_FILE_NAME = "settings.pb"

@Serializable
data class Settings(
    val name: String = "",
)