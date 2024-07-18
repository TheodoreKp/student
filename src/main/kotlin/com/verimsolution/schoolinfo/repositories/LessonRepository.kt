package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Lesson
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.responses.LessonResponse
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface LessonRepository : MongoRepository<Lesson, String> {
    fun findAllByProfessor(professor: Professor, sort: Sort): List<Lesson>
    fun findAllByClassroomContaining(classroom: Classroom, sort: Sort): List<Lesson>
    fun findAllByClassroom(classroom: Classroom, descending: Sort): List<LessonResponse>
}
