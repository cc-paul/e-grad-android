package com.jmr.e_grad.data

data class insertResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: Any?
)
