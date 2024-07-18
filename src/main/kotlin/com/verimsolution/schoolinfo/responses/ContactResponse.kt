package com.verimsolution.schoolinfo.responses

data class ContactResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val type: String,
    val image: String?
)
