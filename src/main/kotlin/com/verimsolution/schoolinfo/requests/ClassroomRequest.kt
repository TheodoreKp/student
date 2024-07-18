package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import java.io.Serializable

@Data
data class ClassroomRequest(
    @field:NotNull @field:NotBlank
    val name: String? = null,
    @field:NotNull @field:NotBlank
    val description: String? = null,
    @field:NotNull @field:NotBlank
    val teacher: String? = null
) : Serializable
