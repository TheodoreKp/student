package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.*
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface ForumRepository: MongoRepository<Forum, String> {
    fun findAllByProfessor(professor: Professor, sort: Sort): List<Forum>
    fun findAllByStudent(student: Student, sort: Sort): List<Forum>
    fun findAllByClassroom(classroom: Classroom, sort: Sort): List<Forum>
}
