package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.ForumRequest
import com.verimsolution.schoolinfo.responses.ForumResponse
import org.springframework.security.core.Authentication

interface ForumService {
     fun listProfessorForum(authentication: Authentication): List<ForumResponse>
     fun listStudentForum(authentication: Authentication): List<ForumResponse>
     fun listProfessorClassroomsForum(authentication: Authentication): List<ForumResponse>
     fun listStudentClassroomForum(authentication: Authentication): List<ForumResponse>
      fun saveProfessorForum(request: ForumRequest): ForumResponse
      fun saveStudentForum(request: ForumRequest): ForumResponse
      fun showForum(id: String): ForumResponse
      fun updateForum(id: String, request: ForumRequest): ForumResponse
      fun destroyForum(id: String): Any
}
