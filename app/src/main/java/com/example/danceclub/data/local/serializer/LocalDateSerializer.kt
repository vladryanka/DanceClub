package com.example.danceclub.data.local.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        val seconds = value.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
        encoder.encodeLong(seconds)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val timestamp = decoder.decodeLong()
        return Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDate()
    }
}
