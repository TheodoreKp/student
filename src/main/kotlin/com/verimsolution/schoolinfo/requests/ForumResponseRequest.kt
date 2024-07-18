package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data

@Data
data class ForumResponseRequest(
    @field:NotNull @field:NotBlank
    val description: String? = null
)
