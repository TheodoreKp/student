package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.config.security.StudentAuthenticationProvider
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.requests.StudentRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.StudentService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(STUDENT_API_BASE_URL, produces = [APPLICATION_JSON_VALUE])
class StudentAuthController(
    private val service: StudentService,
    private val authenticationProvider: StudentAuthenticationProvider,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider
) {

    @PostMapping("refresh/token", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun refreshToken(@RequestParam("token") token: String): ResponseEntity<HttpResponse> = successResponse(
        "Token is updated successfully", OK, service.refreshToken(token, jwtAuthenticationProvider)
    )

    @PostMapping("me")
    fun me(): ResponseEntity<HttpResponse> = successResponse(
        "Logged in User Details", OK, service.authUser(SecurityContextHolder.getContext().authentication)
    )

    @PostMapping("login", consumes = [APPLICATION_JSON_VALUE])
    fun login(@RequestBody @Valid request: StudentLoginRequest): ResponseEntity<HttpResponse> = successResponse(
        "User is login", OK, service.loginUser(request, authenticationProvider)
    )

    @PostMapping("register", consumes = [APPLICATION_JSON_VALUE])
    fun register(
        @RequestBody @Valid request: StudentRequest, result: BindingResult
    ): ResponseEntity<HttpResponse> = successResponse(
        "Account is created successfully", CREATED, service.saveStudent(request)
    )
}
