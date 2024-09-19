package com.jmr.e_grad.data

import com.google.gson.annotations.SerializedName

data class YearBookRelatedData(
    @SerializedName("mode") val mode: String = "",
    @SerializedName("schoolYear") val schoolYear: Int = 0,
    @SerializedName("courseId") val courseId: Int = 0,
    @SerializedName("search") val search: String = ""
)
