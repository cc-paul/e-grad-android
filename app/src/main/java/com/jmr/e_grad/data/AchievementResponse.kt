package com.jmr.e_grad.data

data class AchievementResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: Data
)

data class Data(
    val rows_returned: Int,
    val achievement: List<Achievement>
)

data class Achievement(
    val studentNumber: String,
    val fullName: String,
    val course: String,
    val id: Int,
    val achievement: List<StudentAchievement>
)

data class StudentAchievement(
    val id: Int,
    val studentId: Int,
    val titleName: String,
    val dateReceived: String,
    val remarks: String?,
    val isAwardee: Int
)