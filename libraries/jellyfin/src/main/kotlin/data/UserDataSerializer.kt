package data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import models.LogInState
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<LogInState> {
    override val defaultValue: LogInState = LogInState.NotLoggedIn

    override suspend fun readFrom(input: InputStream): LogInState {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: LogInState,
        output: OutputStream,
    ) {
        val encodeToByteArray = ProtoBuf.encodeToByteArray(t)
        output.write(encodeToByteArray)
    }
}

const val USER_DATA_FILE = "UserDataSerializer.pb"