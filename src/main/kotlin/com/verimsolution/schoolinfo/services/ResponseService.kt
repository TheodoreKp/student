package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.ForumResponseRequest
import com.verimsolution.schoolinfo.responses.ResponseResponse

interface ResponseService {
    fun listResponseByList(forumId: String): List<ResponseResponse>
    fun saveProfessorResponse(forumId: String, request: ForumResponseRequest): ResponseResponse
    fun saveStudentResponse(forumId: String, request: ForumResponseRequest): ResponseResponse
    fun removeResponse(id: String): Any
    fun validResponse(id: String): ResponseResponse
}
