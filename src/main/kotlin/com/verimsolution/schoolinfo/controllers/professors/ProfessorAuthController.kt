package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.config.security.ProfessorAuthenticationProvider
import com.verimsolution.schoolinfo.requests.ProfessorRequest
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ProfessorService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PROFESSOR_API_BASE_URL, produces = [MediaType.APPLICATION_JSON_VALUE])
class ProfessorAuthController(
    private val service: ProfessorService,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
    private val authenticationProvider: ProfessorAuthenticationProvider
) {
    @PostMapping("refresh/token", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun refreshToken(@RequestParam("token") token: String): ResponseEntity<HttpResponse> = successResponse(
        "Token is updated successfully", HttpStatus.OK, service.refreshToken(token, jwtAuthenticationProvider)
    )

    @PostMapping("me")
    fun me(): ResponseEntity<HttpResponse> = successResponse(
        "Logged in User Details",
        HttpStatus.OK, service.authProfessor(SecurityContextHolder.getContext().authentication)
    )

    @PostMapping("login", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody @Valid request: StudentLoginRequest): ResponseEntity<HttpResponse> = successResponse(
        "User is login", HttpStatus.OK, service.loginProfessor(request, authenticationProvider)
    )

    @PostMapping("register", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody @Valid request: ProfessorRequest): ResponseEntity<HttpResponse> = successResponse(
        "Account is created successfully", CREATED, service.saveProfessor(request)
    )
}
