package com.verimsolution.schoolinfo.requests

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import lombok.Data
import java.io.Serializable
import java.util.*

@Data
data class StudentRequest(
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
    @field:NotNull @field:NotBlank
    val classroomId: String? = null,
    @field:NotNull @field:Past @field:JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: Date? = null,
    @field:NotNull @field:NotBlank @field:Email
    val email: String? = null
) : Serializable
