package com.xzakota.model.media

import okhttp3.MediaType.Companion.toMediaTypeOrNull

object MediaTypePool {
    @JvmField
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

    @JvmField
    val OCTET_STREAM = "application/octet-stream".toMediaTypeOrNull()
}