package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Forum
import com.verimsolution.schoolinfo.entities.Response
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface ResponseRepository : MongoRepository<Response, String> {
    fun countAllByForum(forum: Forum  ): Long
    fun findAllByForum(forum: Forum, sort: Sort): List<Response>
}
