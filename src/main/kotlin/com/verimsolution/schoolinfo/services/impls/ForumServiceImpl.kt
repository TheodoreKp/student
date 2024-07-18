package com.verimsolution.schoolinfo.services.impls

import com.fasterxml.jackson.databind.ObjectMapper
import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Forum
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.Student
import com.verimsolution.schoolinfo.exceptions.ForumNotfoundException
import com.verimsolution.schoolinfo.exceptions.ProfessorNotFoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.ForumRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.requests.ForumRequest
import com.verimsolution.schoolinfo.responses.ForumResponse
import com.verimsolution.schoolinfo.services.ForumService
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ForumServiceImpl(
    private val repository: ForumRepository,
    private val studentRepository: StudentRepository,
    private val classroomRepository: ClassroomRepository,
    private val professorRepository: ProfessorRepository
) : ForumService {
    override fun listProfessorForum(authentication: Authentication): List<ForumResponse> {
        val professor = getProfessor(authentication.name)
        return repository.findAllByProfessor(professor, Sort.by("created_at").descending()).map { it.response() }
    }

    override fun listStudentForum(authentication: Authentication): List<ForumResponse> {
        val student = getStudent(authentication.name)
        return repository.findAllByStudent(student, Sort.by("created_at").descending()).map { it.response() }
    }

    override fun listProfessorClassroomsForum(authentication: Authentication): List<ForumResponse> {
        val professor = getProfessor(authentication.name)
        return repository.findAll(Sort.by("created_at").descending()).filter {
            professor.classrooms.contains(it.classroom) && it.professor != professor
        }.map { it.response() }
    }

    override fun listStudentClassroomForum(authentication: Authentication): List<ForumResponse> {
        val student = getStudent(authentication.name)
        return repository.findAllByClassroom(student.classroom, Sort.by("created_at").descending()).map {
            it.response()
        }
    }

    override fun saveProfessorForum(request: ForumRequest): ForumResponse {
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        val classroom = getClassroom(request.classroomId!!)
        val forum = Forum().request(request).copy(
            professor = professor, classroom = classroom
        )
        return repository.insert(forum).response()
    }

    override fun saveStudentForum(request: ForumRequest): ForumResponse {
        val student = getStudent(SecurityContextHolder.getContext().authentication.name)
        val classroom = getClassroom(request.classroomId!!)
        val forum = Forum().request(request).copy(
            student = student, classroom = classroom
        )
        return repository.insert(forum).response()
    }

    override fun showForum(id: String): ForumResponse = repository.findById(id).map {
        it.response()
    }.orElseThrow {
        ForumNotfoundException("Forum with id $id is not found.")
    }

    override fun updateForum(id: String, request: ForumRequest): ForumResponse = repository.findById(id).map {
        val forum = it.request(request).copy(updatedAt = Date())
        repository.save(forum).response()
    }.orElseThrow {
        ForumNotfoundException("Forum with id $id is not found.")
    }

    override fun destroyForum(id: String): Any = repository.findById(id).map {
        repository.delete(it)
        ObjectMapper()
    }.orElseThrow {
        ForumNotfoundException("Forum with id $id is not found.")
    }

    private fun getProfessor(username: String): Professor = professorRepository.findByUsername(username).orElseThrow {
        ProfessorNotFoundException("Professor with username $username is not found.")
    }

    private fun getStudent(username: String): Student = studentRepository.findByUsername(username).orElseThrow {
        StudentNotFoundException("Student with username $username is not found.")
    }

    private fun getClassroom(id: String): Classroom = classroomRepository.findById(id).orElseThrow {
        ClassroomNotFoundException("Classroom with id $id is not found.")
    }
}
