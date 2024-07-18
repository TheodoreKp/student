package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.Student
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface StudentRepository : MongoRepository<Student, String> {
    fun findByUsername(username: String): Optional<Student>
    fun findAllByClassroomContains(classroom: Classroom): List<Student>
}
