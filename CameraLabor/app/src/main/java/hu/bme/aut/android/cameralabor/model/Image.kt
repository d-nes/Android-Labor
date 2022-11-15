package hu.bme.aut.android.cameralabor.model

import com.squareup.moshi.Json

data class Image(
    @Json(name = "_id") val id: String,
    val name: String,
    val description: String,
    val timestamp: Long,
    val url: String,
    val size: Long,
    val mimetype: String,
    val encoding: String
)