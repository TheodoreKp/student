package com.verimsolution.schoolinfo.responses

import java.util.*

data class ClassroomResponse(
    val id: String,
    val name: String,
    val reference: String,
    val description: String,
    val teacher: String,
    val school: SchoolResponse,
    val createdAt: Date,
    val updatedAt: Date
) {
    constructor() : this(
        id = "",
        name = "",
        reference = "",
        description = "",
        teacher = "",
        school = SchoolResponse(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
