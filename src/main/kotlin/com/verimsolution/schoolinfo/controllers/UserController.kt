package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.UserRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.UserService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SCHOOL_API_BASE_URL, produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val service: UserService
) {

    @PostMapping("register", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun register(@ModelAttribute @Valid userRequest: UserRequest, result: BindingResult): ResponseEntity<HttpResponse> =
        if (result.hasErrors()) validationErrorResponse("", result.fieldErrors) else successResponse(
            "Account is created successfully", CREATED, service.saveUser(userRequest)
        )

    @GetMapping("{email}")
    fun show(@PathVariable email: String): ResponseEntity<HttpResponse> = successResponse(
        "User found successfully", HttpStatus.OK, service.showUser(email)
    )
}
