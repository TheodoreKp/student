package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ClassroomService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(PROFESSOR_API_BASE_URL + "classrooms", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProfessorClassroomController(
    private val service: ClassroomService
) {

    @GetMapping
    fun list(): ResponseEntity<HttpResponse> = successResponse(
        "List classroom", OK, service.listClassroom()
    )
}
