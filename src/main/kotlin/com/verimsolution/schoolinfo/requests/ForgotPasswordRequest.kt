package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Data
import java.io.Serializable

@Data
data class ForgotPasswordRequest(
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null,
    @field:NotNull @field:NotBlank @field:Size(min = 8)
    val password: String? = null,
    @field:NotNull @field:NotBlank @field:Size(min = 8)
    val confirmPassword: String? = null
) : Serializable
