package com.verimsolution.schoolinfo.responses

import java.util.*

data class ForumResponse(
    val id: String,
    val title: String,
    val description: String,
    val professor: ProfessorResponse?,
    val student: StudentResponse?,
    val views: Int,
    val classroom: ClassroomResponse,
    val responses: Long,
    val createdAt: Date,
    val solve: Boolean
) {

    constructor() : this(
        id = "",
        title = "",
        description = "",
        professor = null,
        student = null,
        views = 0,
        classroom = ClassroomResponse(),
        responses = 0,
        createdAt = Date(),
        solve = false
    )
}
