package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
}