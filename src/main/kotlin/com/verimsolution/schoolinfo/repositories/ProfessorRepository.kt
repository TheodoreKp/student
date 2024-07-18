package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Professor
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ProfessorRepository : MongoRepository<Professor, String> {
    fun findByUsername(username: String): Optional<Professor>
}
