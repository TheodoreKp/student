package com.verimsolution.schoolinfo.responses

import java.util.*

data class ProfessorResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val phone: String,
    val verify: Boolean,
    val isNotLocked: Boolean,
    val emailVerify: Boolean,
    val avatar: String?,
    val genre: String,
    val roles: Collection<RoleResponse>,
    val classrooms: Collection<ClassroomResponse>,
    val createdAt: String,
    val updatedAt: String
) {
    constructor() : this(
        id = "",
        firstName = "",
        lastName = "",
        username = "",
        email = "",
        phone = "",
        verify = false,
        isNotLocked = false,
        emailVerify = false,
        avatar = null,
        genre = "",
        roles = emptyList(),
        classrooms = emptyList(),
        createdAt = Date().toString(),
        updatedAt = Date().toString()
    )
}
