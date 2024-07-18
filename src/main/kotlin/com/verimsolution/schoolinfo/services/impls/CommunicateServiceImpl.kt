package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Communicate
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.exceptions.CommunicateNotFoundException
import com.verimsolution.schoolinfo.exceptions.ProfessorNotFoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.CommunicateRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.requests.CommunicateRequest
import com.verimsolution.schoolinfo.responses.CommunicateResponse
import com.verimsolution.schoolinfo.services.CommunicateService
import com.verimsolution.schoolinfo.utils.AppConverter
import com.verimsolution.schoolinfo.utils.emuns.CommunicateType
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class CommunicateServiceImpl(
    private val converter: AppConverter,
    private val repository: CommunicateRepository,
    private val studentRepository: StudentRepository,
    private val classroomRepository: ClassroomRepository,
    private val professorRepository: ProfessorRepository
) : CommunicateService {

    override fun getCommunicates(schoolId: String): List<CommunicateResponse> = repository
        .findAll(Sort.by(Sort.Direction.DESC, "created_at"))
        .map { it.response() }

    override fun getCommunicates(schoolId: String, classroomId: String?): List<CommunicateResponse> = repository
        .findAll(Sort.by(Sort.Direction.DESC, "created_at"))
        .filter {
            it.type == CommunicateType.GLOBAL ||
                    (it.type == CommunicateType.MULTIPLE && classroomId != null && it.classrooms.contains(
                        getClassroomById(classroomId)
                    ))
        }
        .map {
            it.response()
        }

    override fun saveCommunicate(request: CommunicateRequest): CommunicateResponse {
        val communicate = Communicate().request(request).copy(valid = true)
        addClassrooms(communicate, request)
        return repository.insert(communicate).response()
    }

    override fun saveProfessorCommunicate(request: CommunicateRequest): CommunicateResponse {
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        val communicate = Communicate().request(request).copy(professor= professor)
        addClassrooms(communicate, request)
        return repository.insert(communicate).response()
    }

    override fun showCommunicate(id: String): CommunicateResponse = repository.findById(id).map {
        it.response()
    }.orElseThrow {
        throw CommunicateNotFoundException("Communicate not found")
    }

    override fun updateCommunicate(id: String, request: CommunicateRequest): CommunicateResponse =
        repository.findById(id).map { communicate ->
            val data = communicate.request(request).copy(
                updatedAt = Date()
            )
            addClassrooms(data, request)
            repository.save(data).response()
        }.orElseThrow {
            throw CommunicateNotFoundException("Communicate not found")
        }

    override fun studentCommunicates(authentication: Authentication): List<CommunicateResponse> {
        val user = studentRepository.findByUsername(authentication.name).orElseThrow {
            StudentNotFoundException("Student with identifier ${authentication.name} is not found")
        }
        return repository.findAll(Sort.by("created_at").descending()).filter {
            (it.classrooms.contains(user.classroom) && it.type == CommunicateType.MULTIPLE) || it.type == CommunicateType.GLOBAL
        }.map { it.response() }
    }

    override fun professorCommunicates(authentication: Authentication): List<CommunicateResponse> {
        val professor = getProfessor(authentication.name)
        return repository.findAllByProfessor(professor, Sort.by("created_at").descending())
            .map { it.response() }
    }

    override fun adminCommunicates(authentication: Authentication): List<CommunicateResponse> = repository.findAll()
        .filter { it.type == CommunicateType.GLOBAL && it.professor == null }.map { it.response() }


    private fun getClassroomById(id: String): Classroom = classroomRepository.findById(id).orElseThrow {
        ClassroomNotFoundException("Classroom not found")
    }

    private fun addClassrooms(communicate: Communicate, request: CommunicateRequest) {
        if (communicate.type == CommunicateType.MULTIPLE && request.classroomsId.isEmpty()) {
            throw IllegalArgumentException("Classrooms not be found")
        }
        if (communicate.type == CommunicateType.MULTIPLE && request.classroomsId.isNotEmpty()) {
            request.classroomsId.map { getClassroomById(it) }.map {
                communicate.classrooms.add(it)
            }
        }
    }

    private fun getProfessor(username: String): Professor = professorRepository.findByUsername(username).orElseThrow {
        ProfessorNotFoundException("Professor with username $username is not found.")
    }
}
