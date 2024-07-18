package com.verimsolution.schoolinfo.responses

import java.util.*

data class PostCategoryResponse(
    val id: String,
    val name: String,
    val createdAt: Date
) {
    constructor() : this(
        id = "",
        name = "",
        createdAt = Date()
    )
}
