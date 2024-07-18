package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class ProfessorRequest(
    @field:NotNull @field:NotBlank
    val username: String? = null,
    @field:NotNull @field:NotBlank
    val password: String? = null,
    @field:NotNull @field:NotBlank
    val confirmPassword: String? = null,
    @field:NotNull @field:NotBlank
    val firstName: String? = null,
    @field:NotNull @field:NotBlank
    val lastName: String? = null,
    @field:NotNull @field:NotBlank
    val genre: String? = null,
    @field:NotNull @field:NotBlank
    val phone: String? = null,
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null
)
