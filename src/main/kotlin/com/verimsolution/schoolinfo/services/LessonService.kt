package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.LessonRequest
import com.verimsolution.schoolinfo.requests.LessonUploadFileRequest
import com.verimsolution.schoolinfo.requests.UpdateLessonRequest
import com.verimsolution.schoolinfo.responses.LessonResponse
import org.springframework.security.core.Authentication

interface LessonService {
    fun listProfessorLessons(authentication: Authentication): List<LessonResponse>
    fun listStudentLessons(authentication: Authentication): List<LessonResponse>
    fun storeLesson(request: LessonRequest): LessonResponse
    fun showLesson(id: String): LessonResponse
    fun updateLesson(request: UpdateLessonRequest, id: String): LessonResponse
    //Nouveaux ajoutée
    fun listAllLessons(): List<LessonResponse>
    fun storeFile(request: LessonUploadFileRequest, authentication: Authentication): LessonResponse
    fun deleteLesson(id: String, authentication: Authentication)
    fun findAllByClassroom(classroomId: String): List<LessonResponse>
}
