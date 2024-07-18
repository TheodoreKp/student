package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.requests.LessonRequest
import com.verimsolution.schoolinfo.requests.UpdateLessonRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.responses.LessonResponse
import com.verimsolution.schoolinfo.services.LessonService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(STUDENT_API_BASE_URL + "lessons", produces = [MediaType.APPLICATION_JSON_VALUE])
class StudentLessonController(
    private val service: LessonService
) {

    @GetMapping
    fun list(): ResponseEntity<HttpResponse> = successResponse(
        "List lesson",
        HttpStatus.OK, service.listStudentLessons(SecurityContextHolder.getContext().authentication)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Lesson details successfully", HttpStatus.OK, service.showLesson(id)
    )

    //Nouveauté apportée
    @GetMapping("Lessons")
    fun listAllLessons(): ResponseEntity<HttpResponse> = successResponse(
        "List All lesson",
        HttpStatus.OK, service.listAllLessons()
    )
    @GetMapping("GetLessonsByClassroom/{classroomId}")
    fun listAllByClassroom(@RequestParam classroomId: String): List<LessonResponse>{
        return service.findAllByClassroom(classroomId)
    }

}