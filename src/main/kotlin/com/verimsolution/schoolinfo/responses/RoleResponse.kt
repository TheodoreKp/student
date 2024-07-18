package com.verimsolution.schoolinfo.responses

data class RoleResponse(
    val id: String,
    val name: String
) {
    constructor() : this(
        id = "",
        name = ""
    )
}
