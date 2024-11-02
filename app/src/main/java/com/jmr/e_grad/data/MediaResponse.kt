package com.jmr.e_grad.data

data class MediaResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: MediaResponseData
)

data class MediaResponseData(
    val rows_returned: Int,
    val media: List<MediaData>
)

data class MediaData(
    val schoolYear: String,
    val fileName: String,
    val description: String,
    val type: String,
    val isCoverPhoto: Int
)