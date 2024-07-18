package com.verimsolution.schoolinfo.responses

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)
