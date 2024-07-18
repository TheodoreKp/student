package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.ClassroomNotFoundException
import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.entities.School
import com.verimsolution.schoolinfo.repositories.ClassroomRepository
import com.verimsolution.schoolinfo.repositories.SchoolRepository
import com.verimsolution.schoolinfo.requests.ClassroomRequest
import com.verimsolution.schoolinfo.responses.ClassroomResponse
import com.verimsolution.schoolinfo.services.ClassroomService
import com.verimsolution.schoolinfo.utils.AppConverter
import com.verimsolution.schoolinfo.utils.response
import lombok.AllArgsConstructor
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
@AllArgsConstructor
class ClassroomServiceImpl(
    private val converter: AppConverter,
    private val repository: ClassroomRepository,
    private val schoolRepository: SchoolRepository
) : ClassroomService {

    override fun getClassroomsBySchool(schoolId: String): List<ClassroomResponse> {
        val school = getSchoolById(schoolId)
        return repository.findAllBySchool(school, Sort.by("created_at").descending()).map {
            it.response()
        }
    }


    override fun saveClassroom(schoolId: String, request: ClassroomRequest): ClassroomResponse {
        val school = getSchoolById(schoolId)
        val classroom = converter.classroomRequestToClassroom(request).copy(
            school = school,
            reference = UUID.randomUUID().toString()
        )
        return repository.insert(classroom).response()
    }

    override fun showClassroom(id: String): ClassroomResponse = repository.findById(id).map {
       it.response()
    }.orElseThrow {
        throw ClassroomNotFoundException("Classroom not found")
    }

    override fun updateClassroom(id: String, request: ClassroomRequest): ClassroomResponse =
        repository.findById(id).map { classroom ->
            val classrooms = converter.classroomRequestToClassroom(classroom, request).copy(
                updatedAt = Date()
            )
            repository.save(classrooms).response()
        }.orElseThrow {
            throw ClassroomNotFoundException("Classroom not found")
        }

    override fun listClassroom(): List<ClassroomResponse> = repository.findAll().map {
        it.response()
    }

    private fun getSchoolById(schoolId: String): School = schoolRepository.findById(schoolId).orElseThrow {
        throw SchoolNotFoundException("School not found")
    }
}
