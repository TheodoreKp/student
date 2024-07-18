package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.schoolinfo.config.security.ProfessorAuthenticationProvider
import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.Role
import com.verimsolution.schoolinfo.exceptions.RoleNotFoundException
import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.RoleRepository
import com.verimsolution.schoolinfo.requests.ProfessorClassroomsRequest
import com.verimsolution.schoolinfo.requests.ProfessorRequest
import com.verimsolution.schoolinfo.requests.StudentLoginRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.ProfessorResponse
import com.verimsolution.schoolinfo.services.ProfessorService
import com.verimsolution.schoolinfo.utils.JwtUtils
import com.verimsolution.schoolinfo.utils.ProfessorPrincipal
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Sort
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
@Qualifier("professorServiceImpl")
class ProfessorServiceImpl(
    private val utils: JwtUtils,
    private val roleRepository: RoleRepository,
    private val repository: ProfessorRepository,
    private val passwordEncoder: PasswordEncoder,
    private val classroomRepository: ClassroomRepository
) : ProfessorService {
    override fun getProfessors(schoolId: String): List<ProfessorResponse> {
        TODO("Not yet implemented")
    }

    override fun saveProfessor(request: ProfessorRequest): ProfessorResponse {
        val professor = Professor().request(request).let {
            it.copy(
                reference = UUID.randomUUID().toString().uppercase(),
                password = passwordEncoder.encode(it.password),
            )
        }
        professor.roles.add(getRole())
        return repository.insert(professor).response()
    }

    override fun showProfessor(id: String): ProfessorResponse {
        TODO("Not yet implemented")
    }

    override fun updateProfessor(id: String, request: ProfessorRequest): ProfessorResponse {
        TODO("Not yet implemented")
    }

    override fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse {
        TODO("Not yet implemented")
    }

    override fun authProfessor(authentication: Authentication): ProfessorResponse = repository
        .findByUsername(authentication.name).map { it.response() }.orElseThrow {
            UserNotfoundException("User not found.")
        }

    override fun loginProfessor(
        request: StudentLoginRequest,
        authenticationProvider: ProfessorAuthenticationProvider
    ): JwtResponse = authenticationProvider.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(request.username, request.password)
    ).let {
        utils.getJwtToken(it)
    }

    override fun listAllProfessor(): List<ProfessorResponse> = repository.findAll(Sort.by("created_at").descending())
        .map { it.response() }

    override fun lockedProfessor(id: String): ProfessorResponse = repository.findById(id).map {
        val professor = it.copy(isNotLocked = false, updatedAt = Date.from(Instant.now()))
        repository.save(professor).response()
    }.orElseThrow {
        UserNotfoundException("Professor with id $id is not found.")
    }

    override fun unlockedProfessor(id: String): ProfessorResponse = repository.findById(id).map {
        val professor = it.copy(isNotLocked = true, updatedAt = Date.from(Instant.now()))
        repository.save(professor).response()
    }.orElseThrow {
        UserNotfoundException("Professor with id $id is not found.")
    }

    override fun enabledProfessor(id: String): ProfessorResponse = repository.findById(id).map {
        val professor = it.copy(verify = true, updatedAt = Date.from(Instant.now()))
        repository.save(professor).response()
    }.orElseThrow {
        UserNotfoundException("Professor with id $id is not found.")
    }

    override fun disabledProfessor(id: String): ProfessorResponse = repository.findById(id).map {
        val professor = it.copy(verify = false, updatedAt = Date.from(Instant.now()))
        repository.save(professor).response()
    }.orElseThrow {
        UserNotfoundException("Professor with id $id is not found.")
    }

    override fun addProfessorClassrooms(id: String, request: ProfessorClassroomsRequest): ProfessorResponse = repository
        .findById(id).map {
            val classrooms = request.classroomsId!!.map { id -> getClassroom(id) }.toMutableList()
            val professor = it.copy(classrooms = classrooms, updatedAt = Date.from(Instant.now()))
            repository.save(professor).response()
        }.orElseThrow {
            UserNotfoundException("Professor with id $id is not found.")
        }

    override fun listProfessorFriends(): Any {
        TODO("Not yet implemented")
    }

    override fun loadUserByUsername(username: String): UserDetails = repository.findByUsername(username)
        .map { ProfessorPrincipal(it) }.orElseThrow { RoleNotFoundException("Role not found") }

    private fun getRole(): Role = roleRepository.findByName("ROLE_TEACHER").orElseThrow {
        RoleNotFoundException("Role not found")
    }

    private fun getClassroom(id: String): Classroom = classroomRepository.findById(id).orElseThrow {
        ClassroomNotFoundException("Classroom with id $id is not found")
    }
}
