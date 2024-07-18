package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.ForgotPasswordRequest
import com.verimsolution.schoolinfo.requests.LoginRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.UserService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SCHOOL_API_BASE_URL, produces = [APPLICATION_JSON_VALUE])
class AuthController(
    private val service: UserService,
    private val authenticationProvider: DaoAuthenticationProvider,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider
) {
    @PostMapping(value = ["refresh/token"], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun refreshToken(@RequestParam("token") token: String): ResponseEntity<HttpResponse> =
        successResponse(
            "Token is updated successfully", OK, service.refreshToken(token, jwtAuthenticationProvider)
        )

    @PostMapping("me")
    fun me(): ResponseEntity<HttpResponse> = successResponse(
        "Logged in User Details", OK, service.authUser(SecurityContextHolder.getContext().authentication)
    )

    @PostMapping(value = ["forgot/password"], consumes = [APPLICATION_JSON_VALUE])
    fun forgetPassword(
        @RequestBody @Valid request: ForgotPasswordRequest
    ): ResponseEntity<HttpResponse> = successResponse(
        "Password update successfully", OK, service.forgotPassword(request)
    )

    @PostMapping(value = ["login"], consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun login(@ModelAttribute @Valid request: LoginRequest): ResponseEntity<HttpResponse> = successResponse(
        "User is login", OK, service.loginUser(request, authenticationProvider)
    )

}
