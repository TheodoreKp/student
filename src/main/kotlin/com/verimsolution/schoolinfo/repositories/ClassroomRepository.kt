package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Classroom
import com.verimsolution.schoolinfo.entities.School
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface ClassroomRepository : MongoRepository<Classroom, String> {
    fun findAllBySchool(school: School, sort: Sort): List<Classroom>
}
