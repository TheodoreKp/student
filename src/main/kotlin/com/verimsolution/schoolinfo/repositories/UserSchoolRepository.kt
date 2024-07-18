package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.UserSchool
import org.springframework.data.mongodb.repository.MongoRepository

interface UserSchoolRepository : MongoRepository<UserSchool, String> {
}
