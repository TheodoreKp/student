package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.PostRequest
import com.verimsolution.schoolinfo.responses.PostResponse

interface PostService {
     fun getPosts(schoolId: String): List<PostResponse>
     fun storePost(schoolId: String, request: PostRequest): PostResponse
     fun showPost(id: String): PostResponse
     fun updatePost(id: String, request: PostRequest): PostResponse
      fun listAllPost(): List<PostResponse>
      fun limitedPost(limit: Int): List<PostResponse>
}