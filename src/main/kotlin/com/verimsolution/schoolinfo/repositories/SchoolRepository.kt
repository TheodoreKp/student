package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.School
import org.springframework.data.mongodb.repository.MongoRepository

interface SchoolRepository : MongoRepository<School, String>