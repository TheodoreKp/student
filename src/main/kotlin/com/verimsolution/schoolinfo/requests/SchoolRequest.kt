package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import java.io.Serializable

@Data
data class SchoolRequest(
    @field:NotNull @field:NotBlank
    val name: String? = null,
    @field:NotNull @field:NotBlank
    val address: String? = null,
    @field:NotNull @field:NotBlank //@field:ValidPhoneNumber
    val phone: String? = null,
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null,
    @field:NotNull @field:NotBlank
    val principal: String? = null
) : Serializable
