package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.*
import lombok.Data
import java.io.Serializable

@Data
data class UserRequest(
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null,
    @field:NotNull @field:NotBlank //@field:ValidPhoneNumber
    val phone: String? = null,
    @field:NotNull @field:NotBlank
    val username: String? = null,
    @field:NotNull @field:NotBlank @field:Size(min = 8)
    var password: String? = null,
    @field:NotNull @field:NotBlank @field:Size(min = 8)
    val confirmPassword: String? = null,
    @field:NotNull @field:AssertTrue
    val verify: Boolean? = null
) : Serializable

