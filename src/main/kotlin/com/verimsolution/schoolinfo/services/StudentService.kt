package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.config.security.StudentAuthenticationProvider
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.requests.StudentRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.StudentResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider

interface StudentService : UserDetailsService {
    fun getStudents(schoolId: String): List<StudentResponse>
    fun saveStudent(request: StudentRequest): StudentResponse
    fun showStudent(id: String): StudentResponse
    fun updateStudent(id: String, request: StudentRequest): StudentResponse
    fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse
    fun authUser(authentication: Authentication): StudentResponse
    fun loginUser(request: StudentLoginRequest, authenticationProvider: StudentAuthenticationProvider): JwtResponse
    fun lockedStudent(id: String): StudentResponse
    fun unlockedStudent(id: String): StudentResponse
    fun enabledStudent(id: String): StudentResponse
    fun disabledStudent(id: String): StudentResponse
}
