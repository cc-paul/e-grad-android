package com.jmr.e_grad.data

data class courseResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: CourseResponseData
)

data class CourseResponseData(
    val rows_returned: Int,
    val course: List<CourseData>
)

data class CourseData(
    val id: Int,
    val course: String,
    val description: String
)