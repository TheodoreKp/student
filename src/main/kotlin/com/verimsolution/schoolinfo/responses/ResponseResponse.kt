package com.verimsolution.schoolinfo.responses

import java.util.*

data class ResponseResponse(
    val id: String,
    val description: String,
    val professor: ProfessorResponse?,
    val student: StudentResponse?,
    val solution: Boolean,
    val forum: ForumResponse,
    val createdAt: Date,
    val updatedAt: Date
) {
    constructor() : this(
        id = "",
        description = "",
        professor = null,
        student = null,
        solution = false,
        forum = ForumResponse(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
