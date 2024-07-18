package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.PostCategory
import com.verimsolution.schoolinfo.entities.School
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface PostCategoryRepository : MongoRepository<PostCategory, String> {
    fun findAllBySchool(school: School, sort: Sort): List<PostCategory>
}
