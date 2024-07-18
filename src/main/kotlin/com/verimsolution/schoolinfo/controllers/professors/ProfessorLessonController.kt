package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.requests.LessonRequest
import com.verimsolution.schoolinfo.requests.LessonUploadFileRequest
import com.verimsolution.schoolinfo.requests.UpdateLessonRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.responses.LessonResponse
import com.verimsolution.schoolinfo.services.LessonService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(PROFESSOR_API_BASE_URL + "lessons", produces = [APPLICATION_JSON_VALUE])
class ProfessorLessonController(
    private val service: LessonService
) {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    fun list(): ResponseEntity<HttpResponse> = successResponse(
        "List lesson",
        OK, service.listProfessorLessons(SecurityContextHolder.getContext().authentication)
    )

    @PostMapping("", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun store(@ModelAttribute @Valid request: LessonRequest, result: BindingResult): ResponseEntity<HttpResponse> =
        if (result.hasErrors()) validationErrorResponse("Validation errors", result.fieldErrors)
        else successResponse("Lesson is created successfully", CREATED, service.storeLesson(request))

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Lesson details successfully", OK, service.showLesson(id)
    )

    @PutMapping("{id}", consumes = [APPLICATION_JSON_VALUE])
    fun update(
        @RequestBody @Valid request: UpdateLessonRequest, @PathVariable id: String
    ): ResponseEntity<HttpResponse> = successResponse(
        "Lesson is created successfully", OK, service.updateLesson(request, id)
    )
    //Nouveauté apportée
    @GetMapping("Lessons")
    fun listAllLessons(): ResponseEntity<HttpResponse> = successResponse(
        "List All lesson",
        HttpStatus.OK, service.listAllLessons()
    )

    @PostMapping("uploadFile", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun storeFile(@ModelAttribute @Valid request: LessonUploadFileRequest, result: BindingResult): ResponseEntity<HttpResponse> =
        if (result.hasErrors()) validationErrorResponse("Validation errors", result.fieldErrors)
        else successResponse("File is Uploaded successfully", CREATED, service.storeFile
            (request, SecurityContextHolder.getContext().authentication))

    @GetMapping("GetLessonsByClassroom/{classroomId}")
    fun listAllByClassroom(@RequestParam classroomId: String): List<LessonResponse>{
        return service.findAllByClassroom(classroomId)
    }
}

