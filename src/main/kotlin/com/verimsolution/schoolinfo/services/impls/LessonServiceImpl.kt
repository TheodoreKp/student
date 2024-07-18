package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Lesson
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.Student
import com.verimsolution.schoolinfo.exceptions.LessonNotFoundException
import com.verimsolution.schoolinfo.exceptions.ProfessorNotFoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.LessonRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.requests.LessonRequest
import com.verimsolution.schoolinfo.requests.LessonUploadFileRequest
import com.verimsolution.schoolinfo.requests.UpdateLessonRequest
import com.verimsolution.schoolinfo.responses.LessonResponse
import com.verimsolution.schoolinfo.services.FileService
import com.verimsolution.schoolinfo.services.LessonService
import com.verimsolution.schoolinfo.utils.request
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class LessonServiceImpl(
    private val fileService: FileService,
    private val repository: LessonRepository,
    private val studentRepository: StudentRepository,
    private val professorRepository: ProfessorRepository,
    private val classroomRepository: ClassroomRepository
) : LessonService {
    override fun listProfessorLessons(authentication: Authentication): List<LessonResponse> {
        val professor = getProfessor(authentication.name)
        return repository.findAllByProfessor(professor, Sort.by("created_at").descending())
            .map { it.response() }
    }

    override fun listStudentLessons(authentication: Authentication): List<LessonResponse> {
        val student = getStudent(authentication.name)
        return repository.findAll(Sort.by("created_at").descending())
            .filter { it.classroom == student.classroom }
            .map { it.response() }
    }


    override fun storeLesson(request: LessonRequest): LessonResponse {
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        val classroom = getClassroom(request.classroomId!!)
        if (!professor.classrooms.contains(classroom)) {
            throw ClassroomNotFoundException("You do not publish lessons for this classroom")
        }
        val lesson = Lesson().request(request).copy(
            professor = professor,
            classroom = classroom,
            cover = fileService.saveFile(request.cover!!, "lesson_${UUID.randomUUID()}", "lessons/")
        )
        return repository.insert(lesson).response()
    }

    override fun showLesson(id: String): LessonResponse = repository.findById(id).map { it.response() }.orElseThrow {
        LessonNotFoundException("Lesson with id $id is not found.")
    }

    override fun updateLesson(request: UpdateLessonRequest, id: String): LessonResponse = repository.findById(id)
        .map {
            val lesson = it.request(request).copy(updatedAt = Date.from(Instant.now()))
            repository.save(lesson).response()
        }
        .orElseThrow { LessonNotFoundException("Lesson with id $id is not found.") }

    private fun getProfessor(username: String): Professor = professorRepository.findByUsername(username).orElseThrow {
        ProfessorNotFoundException("Professor with username $username is not found.")
    }

    private fun getStudent(username: String): Student = studentRepository.findByUsername(username).orElseThrow {
        StudentNotFoundException("Student with username $username is not found.")
    }

    private fun getClassroom(classroomId: String): Classroom = classroomRepository.findById(classroomId).orElseThrow {
        ClassroomNotFoundException("Classroom with id $classroomId is not found.")
    }

    //Nouveauté apportée
    override fun listAllLessons(): List<LessonResponse> {
        return repository.findAll(Sort.by("created_at").descending()).map { it.response() }
    }

    override fun storeFile(request: LessonUploadFileRequest, authentication: Authentication): LessonResponse {
        if (request.file == null) {
            throw IllegalArgumentException("Image is required")
        }
        val lesson= repository.findById(request.id!!).orElseThrow {
            LessonNotFoundException("Lesson not found")
        }
        val professeur = getProfessor(authentication.name)
        if (!lesson.professor.equals(professeur)) {
            throw LessonNotFoundException("You do not publish files for this lesson")
        }
        val image = fileService.saveFile(request.file, "lesson_${lesson.name}_${UUID.randomUUID()}", "lessons/")
        lesson.files.add(image)
        return repository.save(lesson).response()
    }

    override fun deleteLesson(id: String, authentication: Authentication) {
        val lesson = repository.findById(id).orElseThrow {
            LessonNotFoundException("Lesson not found")
        }
        val professeur = getProfessor(authentication.name)
        if(!professeur.classrooms.contains(lesson.classroom)) {
            throw LessonNotFoundException("You do not publish this lesson")
        }
        repository.deleteById(id)
    }

    override fun findAllByClassroom(classroomId: String): List<LessonResponse> {
        val classroom = getClassroom(classroomId)
        return repository.findAllByClassroom(classroom, Sort.by("created_at").descending())
    }
}
