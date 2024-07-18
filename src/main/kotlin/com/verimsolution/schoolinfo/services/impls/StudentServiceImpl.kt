package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.config.security.StudentAuthenticationProvider
import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Role
import com.verimsolution.schoolinfo.entities.School
import com.verimsolution.schoolinfo.entities.Student
import com.verimsolution.schoolinfo.exceptions.RoleNotFoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.RoleRepository
import com.verimsolution.schoolinfo.repositories.SchoolRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.requests.StudentRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.StudentResponse
import com.verimsolution.schoolinfo.services.StudentService
import com.verimsolution.schoolinfo.utils.JwtUtils
import com.verimsolution.schoolinfo.utils.StudentPrincipal
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentServiceImpl(
    private val utils: JwtUtils,
    private val repository: StudentRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val schoolRepository: SchoolRepository,
    private val classroomRepository: ClassroomRepository
) : StudentService {

    override fun getStudents(schoolId: String): List<StudentResponse> {
        val school = getSchoolById(schoolId)
        val classrooms = classroomRepository.findAllBySchool(school, Sort.by("created_at").descending())
        return repository.findAll().filter { classrooms.contains(it.classroom) }.map {
            it.response()
        }
    }

    override fun saveStudent(request: StudentRequest): StudentResponse {
        val classroom = getClassroom(request.classroomId!!)
        val student = Student().request(request).let {
            it.copy(
                classroom = classroom,
                reference = UUID.randomUUID().toString().uppercase(),
                password = passwordEncoder.encode(it.password),
            )
        }
        student.roles.add(getRole())
        return repository.insert(student).response()
    }

    override fun showStudent(id: String): StudentResponse = repository.findById(id).map {
        it.response()
    }.orElseThrow {
        StudentNotFoundException("Student not found")
    }

    override fun updateStudent(id: String, request: StudentRequest): StudentResponse = repository.findById(id)
        .map { student ->
            val currentStudent = student.request(request).copy(updatedAt = Date())
            repository.save(currentStudent).response()
        }.orElseThrow {
            StudentNotFoundException("Student not found")
        }

    override fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse = jwtAuthProvider
        .authenticate(BearerTokenAuthenticationToken(token))
        .let { utils.getJwtToken(it) }

    override fun authUser(authentication: Authentication): StudentResponse = repository
        .findByUsername(authentication.name).map { it.response() }.orElseThrow {
            UserNotfoundException("User not found.")
        }

    override fun loginUser(
        request: StudentLoginRequest, authenticationProvider: StudentAuthenticationProvider
    ): JwtResponse = authenticationProvider.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(request.username, request.password)
    ).let {
        utils.getJwtToken(it)
    }

    override fun lockedStudent(id: String): StudentResponse = repository.findById(id).map {
        val student = it.copy(isNotLocked = false, updatedAt = Date())
        repository.save(student).response()
    }.orElseThrow {
        UserNotfoundException("Student with id $id is not found.")
    }

    override fun unlockedStudent(id: String): StudentResponse = repository.findById(id).map {
        val student = it.copy(isNotLocked = true, updatedAt = Date())
        repository.save(student).response()
    }.orElseThrow {
        UserNotfoundException("Student with id $id is not found.")
    }

    override fun enabledStudent(id: String): StudentResponse = repository.findById(id).map {
        val student = it.copy(verify = true, updatedAt = Date())
        repository.save(student).response()
    }.orElseThrow {
        UserNotfoundException("Student with id $id is not found.")
    }

    override fun disabledStudent(id: String): StudentResponse = repository.findById(id).map {
        val student = it.copy(verify = false, updatedAt = Date())
        repository.save(student).response()
    }.orElseThrow {
        UserNotfoundException("Student with id $id is not found.")
    }

    override fun loadUserByUsername(username: String): UserDetails = repository.findByUsername(username)
        .map { StudentPrincipal(it) }.orElseThrow { StudentNotFoundException("Student not found") }

    private fun getClassroom(id: String): Classroom = classroomRepository.findById(id).orElseThrow {
        ClassroomNotFoundException("Classroom not found")
    }

    private fun getSchoolById(id: String): School = schoolRepository.findById(id).orElseThrow {
        SchoolNotFoundException("School not found")
    }

    private fun getRole(): Role = roleRepository.findByName("ROLE_STUDENT").orElseThrow {
        RoleNotFoundException("Role not found")
    }
}
