package com.example.danceclub.data.local.serializer


import java.time.format.DateTimeFormatter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import java.time.LocalDate
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.ZoneOffset

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        val seconds = value.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
        encoder.encodeLong(seconds)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val seconds = decoder.decodeLong()
        return Instant.ofEpochSecond(seconds).atZone(ZoneOffset.UTC).toLocalDate()
    }
}
