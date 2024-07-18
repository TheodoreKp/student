package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommunicateRequest(
    @field:NotNull @field:NotBlank
    val title: String? = null,
    @field:NotNull @field:NotBlank
    val message: String? = null,
    @field:NotNull
    val classroomsId: List<String> = emptyList(),
    @field:NotNull @field:NotBlank
    val type: String? = null
)
