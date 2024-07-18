package com.verimsolution.schoolinfo.responses

data class UserSchoolResponse(
    val id: String,
    val school: SchoolResponse,
    val enable: Boolean
){

    constructor() : this(
        id = "",
        school = SchoolResponse(),
        enable = true
    )
}
