package com.shalva97.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> createProtobufSerializer(defaultValue: T) = object : Serializer<T> {
    override val defaultValue: T = defaultValue

    override suspend fun readFrom(input: InputStream): T {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: T,
        output: OutputStream,
    ) {
        val encodeToByteArray = ProtoBuf.encodeToByteArray(t)
        withContext(Dispatchers.IO) {
            output.write(encodeToByteArray)
        }
    }
}

const val USER_DATA = "UserDataSerializer.pb"
const val RECENT_SERVERS = "JellyFinServerSerializer.pb"
