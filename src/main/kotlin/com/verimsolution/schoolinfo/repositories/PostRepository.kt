package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Post
import com.verimsolution.schoolinfo.entities.School
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository : MongoRepository<Post, String> {
    fun findAllBySchool(school: School, descending: Sort): List<Post>
}
