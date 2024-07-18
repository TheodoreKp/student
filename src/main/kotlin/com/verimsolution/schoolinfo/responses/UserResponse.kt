package com.verimsolution.schoolinfo.responses

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val phone: String,
    val verify: Boolean,
    val emailVerify: Boolean,
    val roles: Collection<RoleResponse>,
    val schools: Collection<UserSchoolResponse>
) {
    constructor() : this(
        id = "",
        username = "",
        email = "",
        phone = "",
        verify = false,
        emailVerify = false,
        roles = emptyList(),
        schools = emptyList()
    )
}
