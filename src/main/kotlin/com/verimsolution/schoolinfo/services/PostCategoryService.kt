package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.PostCategoryRequest
import com.verimsolution.schoolinfo.responses.PostCategoryResponse

interface PostCategoryService {
    fun getCategories(schoolId: String): List<PostCategoryResponse>
    fun saveCategory(schoolId: String, request: PostCategoryRequest): PostCategoryResponse
    fun showCategory(id: String): PostCategoryResponse
    fun updateCategory(id: String, request: PostCategoryRequest): PostCategoryResponse
}
