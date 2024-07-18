package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.config.security.ProfessorAuthenticationProvider
import com.verimsolution.schoolinfo.requests.ProfessorClassroomsRequest
import com.verimsolution.schoolinfo.requests.ProfessorRequest
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.ProfessorResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider

interface ProfessorService : UserDetailsService {
    fun getProfessors(schoolId: String): List<ProfessorResponse>
    fun saveProfessor(request: ProfessorRequest): ProfessorResponse
    fun showProfessor(id: String): ProfessorResponse
    fun updateProfessor(id: String, request: ProfessorRequest): ProfessorResponse
    fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse
    fun authProfessor(authentication: Authentication): ProfessorResponse
    fun loginProfessor(
        request: StudentLoginRequest, authenticationProvider: ProfessorAuthenticationProvider
    ): JwtResponse

    fun listAllProfessor(): List<ProfessorResponse>
    fun lockedProfessor(id: String): ProfessorResponse
    fun unlockedProfessor(id: String): ProfessorResponse
    fun enabledProfessor(id: String): ProfessorResponse
    fun disabledProfessor(id: String): ProfessorResponse
    fun addProfessorClassrooms(id: String, request: ProfessorClassroomsRequest): ProfessorResponse
    fun listProfessorFriends(): Any
}
