package com.verimsolution.schoolinfo.responses

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class StudentResponse(
    val id: String,
    val username: String,
    val reference: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: Date,
    val genre: String,
    val imageUrl: String?,
    val roles: List<RoleResponse>,
    val verify: Boolean,
    val isNotLocked: Boolean,
    val emailVerify: Boolean,
    val classroom: ClassroomResponse
)
