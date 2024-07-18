package com.verimsolution.schoolinfo.responses

data class SchoolResponse(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val principal: String
){
    constructor():this(
        id = "",
        name = "",
        address = "",
        phone = "",
        email = "",
        principal = ""
    )
}
