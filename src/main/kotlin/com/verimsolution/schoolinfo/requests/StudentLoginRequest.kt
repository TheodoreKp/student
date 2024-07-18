package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import java.io.Serializable

@Data
data class StudentLoginRequest(
    @field:NotNull @field:NotBlank
    val username: String? = null,
    @field:NotNull @field:NotBlank
    val password: String? = null
): Serializable
