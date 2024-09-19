package com.jmr.e_grad.data

data class GetGraduatesResponse (
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: GraduateData
)

data class GraduateData(
    val rows_returned: Int,
    val graduates: List<GraduateGroup>
)

data class GraduateGroup(
    val letter: String,
    val graduates: List<Graduate>
)

data class Graduate(
    val studentId: Int,
    val fullName: String,
    val gradPicFileName: String,
    val folderName: String
)