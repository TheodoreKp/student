package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Role
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RoleRepository : MongoRepository<Role, String> {
    fun findByName(name: String): Optional<Role>
}