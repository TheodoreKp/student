package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Communicate
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.School
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface CommunicateRepository : MongoRepository<Communicate, String> {

//    fun findAllBySchool(school: School, sort: Sort): List<Communicate>
    fun findAllByProfessor(professor: Professor, sort: Sort): List<Communicate>
}
