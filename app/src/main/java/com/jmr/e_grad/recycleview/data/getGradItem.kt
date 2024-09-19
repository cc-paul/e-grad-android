package com.jmr.e_grad.recycleview.data

import com.jmr.e_grad.data.Graduate
import com.jmr.e_grad.data.GraduateGroup

data class getGradItem(
    val letter: String = "",
    val graduates: List<Graduate>
)

data class getPicItem(
    val studentId: Int = 0,
    val fullName: String = "",
    val gradPicFileName: String,
    val folderName: String
)