package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.entities.User
import com.verimsolution.schoolinfo.requests.ForgotPasswordRequest
import com.verimsolution.schoolinfo.requests.LoginRequest
import com.verimsolution.schoolinfo.requests.UserRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.UserResponse
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider

interface UserService : UserDetailsService {
    fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse
    fun authUser(authentication: Authentication): UserResponse
    fun forgotPassword(request: ForgotPasswordRequest): UserResponse
    fun saveUser(request: UserRequest): UserResponse
    fun showUser(email: String): User
    fun loginUser(request: LoginRequest, authenticationProvider: DaoAuthenticationProvider): JwtResponse
}