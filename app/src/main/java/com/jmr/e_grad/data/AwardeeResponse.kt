package com.jmr.e_grad.data

data class AwardeeResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: AwardResponseData
)

data class AwardResponseData(
    val rows_returned: Int,
    val awardee: List<AwardeeData>
)

data class AwardeeData(
    val id: Int,
    val studentId: Int,
    val folderName: String,
    val fileName: String,
    val fullName: String,
    val titleName: String,
    val course: String
)