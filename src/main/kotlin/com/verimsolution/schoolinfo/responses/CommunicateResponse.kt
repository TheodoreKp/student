package com.verimsolution.schoolinfo.responses

import com.fasterxml.jackson.annotation.JsonFormat
import com.verimsolution.schoolinfo.utils.emuns.CommunicateType
import java.util.*

data class CommunicateResponse(
    val id: String,
    val title: String,
    val content: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: Date,
    val view: Int = 0,
    val classrooms: Collection<ClassroomResponse>,
    val type: CommunicateType,
    val professor: ProfessorResponse? = null,
    val valid: Boolean,
)
