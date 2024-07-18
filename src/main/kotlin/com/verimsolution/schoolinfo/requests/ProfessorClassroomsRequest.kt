package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class ProfessorClassroomsRequest(
    @field:NotNull @field:NotEmpty
    val classroomsId: Collection<String>? = null
)
