package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.CommunicateRequest
import com.verimsolution.schoolinfo.responses.CommunicateResponse
import org.springframework.security.core.Authentication

interface CommunicateService {
    fun getCommunicates(schoolId: String): List<CommunicateResponse>
    fun getCommunicates(schoolId: String, classroomId: String?): List<CommunicateResponse>
    fun saveCommunicate(request: CommunicateRequest): CommunicateResponse
    fun saveProfessorCommunicate(request: CommunicateRequest): CommunicateResponse
    fun showCommunicate(id: String): CommunicateResponse
    fun updateCommunicate(id: String, request: CommunicateRequest): CommunicateResponse
    fun studentCommunicates(authentication: Authentication): List<CommunicateResponse>
    fun professorCommunicates(authentication: Authentication): List<CommunicateResponse>
    fun adminCommunicates(authentication: Authentication): List<CommunicateResponse>
}
