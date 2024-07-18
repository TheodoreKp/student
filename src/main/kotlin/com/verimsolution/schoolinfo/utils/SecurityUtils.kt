package com.verimsolution.schoolinfo.utils

import com.verimsolution.schoolinfo.responses.ErrorFieldResponse
import com.verimsolution.schoolinfo.responses.HttpResponse
import org.apache.commons.logging.Log
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.FieldError

const val SCHOOL_API_BASE_URL = "/school/api/v1/"
const val STUDENT_API_BASE_URL = "/student/api/v1/"
const val PROFESSOR_API_BASE_URL = "/professor/api/v1/"
const val AUTHORITIES = "Authorities"
const val USER_IMAGE_PATH = "/blog/images/"
val USER_FOLDER = System.getProperty("user.home") + "/school-info/files/"

fun <T : Any> successResponse(message: String, status: HttpStatusCode, data: T): ResponseEntity<HttpResponse> {
    return ResponseEntity.status(status)
        .body(HttpResponse.Success(status, message, data))
}

fun errorResponse(status: HttpStatus, message: String): ResponseEntity<HttpResponse> {
    return ResponseEntity.status(status)
        .body(HttpResponse.Error(status, message, status.reasonPhrase, null))
}

fun errorResponse(status: HttpStatus, message: String, validations: Any): ResponseEntity<HttpResponse> {
    return ResponseEntity.status(status)
        .body(HttpResponse.Error(status, message, status.reasonPhrase, validations))
}

fun validationErrorResponse(message: String, errors: List<FieldError>): ResponseEntity<HttpResponse> {
    val fieldResponses: List<ErrorFieldResponse> = errors.map { fieldError ->
        ErrorFieldResponse(
            fieldError.field,
            fieldError.defaultMessage!!
        )
    }
    return errorResponse(HttpStatus.BAD_REQUEST, message, fieldResponses)
}

//fun isValidPhoneNumber(phoneNumber: String?): Boolean {
//    return try {
//        val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
//        var parsedPhoneNumber: Phonenumber.PhoneNumber? = null
//        parsedPhoneNumber = phoneNumberUtil.parse(phoneNumber, null)
//        phoneNumberUtil.isValidNumber(parsedPhoneNumber)
//    } catch (e: NumberParseException) {
//        throw RuntimeException(e.getMessage())
//    }
//    return false
//}

fun checkAuthentication(
    userDetails: UserDetails, authentication: UsernamePasswordAuthenticationToken,
    passwordEncoder: PasswordEncoder, messages: MessageSourceAccessor, logger: Log
) {
    if (authentication.credentials == null) {
        logger.debug("Failed to authenticate since no credentials provided")
        throw BadCredentialsException(
            messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials")
        )
    } else {
        val presentedPassword = authentication.credentials.toString()
        if (!passwordEncoder.matches(presentedPassword, userDetails.password)) {
            logger.debug("Failed to authenticate since password does not match stored value")
            throw BadCredentialsException(
                messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"
                )
            )
        }
    }
}

fun getFileExtension(fileName: String): String {
    val dotIndex = fileName.lastIndexOf(".")
    return if (dotIndex == -1 || dotIndex == fileName.length - 1) {
        ""
    } else fileName.substring(dotIndex + 1)
}