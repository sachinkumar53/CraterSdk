package com.crater.android.feature.profile.data

import androidx.datastore.core.Serializer
import com.crater.android.data.model.profile.UserDetails
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class UserDetailSerializer : Serializer<UserDetails> {
    override val defaultValue: UserDetails
        get() = UserDetails()

    override suspend fun readFrom(input: InputStream): UserDetails {
        return try {
            Json.decodeFromString(
                UserDetails.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserDetails, output: OutputStream) {
        try {
            output.write(
                Json.encodeToString(UserDetails.serializer(), t).encodeToByteArray()
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}