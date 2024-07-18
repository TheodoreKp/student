package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import java.io.Serializable

@Data
data class LoginRequest(
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null,
    @field:NotBlank @field:NotNull
    val password: String? = null
) : Serializable