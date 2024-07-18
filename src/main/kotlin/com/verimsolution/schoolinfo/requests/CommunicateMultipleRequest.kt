package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CommunicateMultipleRequest(
    @field:NotNull @field:NotBlank
    val title: String?,
    @field:NotNull @field:NotBlank
    val message: String?,
    @field:NotNull @field:NotEmpty
    val classroomsId: List<String>?
)