package com.verimsolution.schoolinfo.services.impls

import com.fasterxml.jackson.databind.ObjectMapper
import com.verimsolution.schoolinfo.entities.Forum
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.Response
import com.verimsolution.schoolinfo.entities.Student
import com.verimsolution.schoolinfo.exceptions.ForumNotfoundException
import com.verimsolution.schoolinfo.exceptions.ProfessorNotFoundException
import com.verimsolution.schoolinfo.exceptions.ResponseNotfoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ForumRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.ResponseRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.requests.ForumResponseRequest
import com.verimsolution.schoolinfo.responses.ResponseResponse
import com.verimsolution.schoolinfo.services.ResponseService
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ResponseServiceImpl(
    private val repository: ResponseRepository,
    private val forumRepository: ForumRepository,
    private val studentRepository: StudentRepository,
    private val professorRepository: ProfessorRepository
) : ResponseService {

    override fun listResponseByList(forumId: String): List<ResponseResponse> {
        val forum = getForum(forumId)
        return repository.findAllByForum(forum, Sort.by("created_at").descending()).map {
            it.response()
        }
    }

    override fun saveProfessorResponse(forumId: String, request: ForumResponseRequest): ResponseResponse {
        val forum = getForum(forumId)
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        val response = Response().request(request).copy(professor = professor, forum = forum)
        return insertResponse(response, forum)
    }

    override fun saveStudentResponse(forumId: String, request: ForumResponseRequest): ResponseResponse {
        val forum = getForum(forumId)
        val student = getStudent(SecurityContextHolder.getContext().authentication.name)
        val response = Response().request(request).copy(student = student, forum = forum)
        return insertResponse(response, forum)
    }

    override fun removeResponse(id: String): Any = repository.findById(id).map { response ->
        val forum = response.forum
        repository.delete(response)
        val updateForum = forum.copy(updatedAt = Date(), responses = repository.countAllByForum(forum))
        forumRepository.save(updateForum)
        ObjectMapper()
    }.orElseThrow {
        ResponseNotfoundException("Response with id $id is not found.")
    }

    override fun validResponse(id: String): ResponseResponse = repository.findById(id).map {
        val response = it.copy(solution = true, updatedAt = Date())
        repository.save(response).let { res ->
            val forum = res.forum
            forumRepository.save(forum.copy(updatedAt = Date(), solve = true))
            res.response()
        }
    }.orElseThrow {
        ResponseNotfoundException("Response with id $id is not found.")
    }

    private fun getProfessor(username: String): Professor = professorRepository.findByUsername(username).orElseThrow {
        ProfessorNotFoundException("Professor with username $username is not found.")
    }

    private fun getStudent(username: String): Student = studentRepository.findByUsername(username).orElseThrow {
        StudentNotFoundException("Student with username $username is not found.")
    }

    private fun insertResponse(response: Response, forum: Forum) = repository.insert(response).let {
        val updateForum = forum.copy(updatedAt = Date(), responses = repository.countAllByForum(forum))
        forumRepository.save(updateForum)
        it.response()
    }

    private fun getForum(id: String): Forum = forumRepository.findById(id).orElseThrow {
        ForumNotfoundException("Forum with id $id is not found.")
    }
}
