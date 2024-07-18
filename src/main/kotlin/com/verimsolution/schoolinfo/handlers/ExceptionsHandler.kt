package com.verimsolution.schoolinfo.handlers

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.exceptions.*
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.utils.errorResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.io.IOException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@RestControllerAdvice
class ExceptionsHandler {

    companion object {
        private val logs: Logger = Logger.getLogger(ExceptionsHandler::class.java.name)
    }

    private val METHOD_IS_NOT_ALLOWED =
        "Cette méthode de demande n'est pas autorisée sur ce point de terminaison. Veuillez envoyer une demande %s"


    @ExceptionHandler(NoHandlerFoundException::class)
    fun noHandlerFoundException(exception: NoHandlerFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(BAD_REQUEST, exception.message!!)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<HttpResponse> {
        println(exception.allErrors)
        return validationErrorResponse("Validation errors", exception.fieldErrors)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<HttpResponse> {
        val httpMethod = Objects.requireNonNull(exception.supportedHttpMethods).iterator().next()
        return errorResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, httpMethod))
    }

    @ExceptionHandler(IOException::class)
    fun ioException(exception: IOException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(INTERNAL_SERVER_ERROR, exception.message!!)
    }

    @ExceptionHandler(Exception::class)
    fun internalServerErrorException(exception: Exception): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(INTERNAL_SERVER_ERROR, exception.message!!)
    }

    @ExceptionHandler(RuntimeException::class)
    fun internalServerErrorException(exception: RuntimeException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(INTERNAL_SERVER_ERROR, exception.message!!)
    }

    @ExceptionHandler(UserNotfoundException::class)
    fun userNotfoundException(exception: UserNotfoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(SchoolNotFoundException::class)
    fun schoolNotFoundException(exception: SchoolNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(ClassroomNotFoundException::class)
    fun classroomNotFoundException(exception: ClassroomNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun studentNotFoundException(exception: StudentNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(CommunicateNotFoundException::class)
    fun communicateNotFoundException(exception: CommunicateNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(PostCategoryNotFoundException::class)
    fun postCategoryNotFoundException(exception: PostCategoryNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(PostNotFoundException::class)
    fun postNotFoundException(exception: PostNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(ForumNotfoundException::class)
    fun forumNotfoundException(exception: ForumNotfoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(ProfessorNotFoundException::class)
    fun professorNotFoundException(exception: ProfessorNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(ResponseNotfoundException::class)
    fun responseNotFoundException(exception: ResponseNotfoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }

    @ExceptionHandler(LessonNotFoundException::class)
    fun lessonNotFoundException(exception: LessonNotFoundException): ResponseEntity<HttpResponse> {
        logs.log(Level.WARNING, exception.message)
        return errorResponse(NOT_FOUND, exception.message!!)
    }
}
