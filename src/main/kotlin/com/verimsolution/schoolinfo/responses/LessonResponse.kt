package com.verimsolution.schoolinfo.responses

import java.util.*

data class LessonResponse(
    val id: String,
    val name: String,
    val description: String,
    val classroom: ClassroomResponse,
    val professor: ProfessorResponse,
    val cover: String?,
    val files: Collection<String>,
    val reference: String,
    val subject: String,
    val createdAt: Date,
    val updatedAt: Date
){
    constructor() : this(
        id = "",
        name = "",
        description = "",
        classroom = ClassroomResponse(),
        professor = ProfessorResponse(),
        cover = "",
        files = emptyList(),
        createdAt = Date(),
        reference = "",
        updatedAt = Date(),
        subject = ""
    )
}
