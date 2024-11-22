package com.example.danceclub.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.util.Base64

fun isBase64String(input: String): Boolean {
    return try {
        Base64.getDecoder().decode(input)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}

fun base64StringToImageBitmap(base64String: String): ImageBitmap? {
    if (base64String.isBlank()) {
        Log.e("Error", "In base64ToImageBitmap: the importing string is blank")
        return null
    } else if (!isBase64String(base64String)) {
        Log.e("Error", "In base64ToImageBitmap: the importing string is not in base64 format")
        return null
    }

    val stringBytes = Base64.getDecoder().decode(base64String)
    return BitmapFactory.decodeByteArray(stringBytes, 0, stringBytes.size).asImageBitmap()
}

fun uriToBase64String(contentResolver: ContentResolver, uri: Uri): String? {
    val inputStream = contentResolver.openInputStream(uri)
    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    val base64String = Base64.getEncoder().encodeToString(byteArray)

    return base64String
}