package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.entities.School
import com.verimsolution.schoolinfo.exceptions.PostCategoryNotFoundException
import com.verimsolution.schoolinfo.repositories.PostCategoryRepository
import com.verimsolution.schoolinfo.repositories.SchoolRepository
import com.verimsolution.schoolinfo.requests.PostCategoryRequest
import com.verimsolution.schoolinfo.responses.PostCategoryResponse
import com.verimsolution.schoolinfo.services.PostCategoryService
import com.verimsolution.schoolinfo.utils.AppConverter
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class PostCategoryServiceImpl(
    private val converter: AppConverter,
    private val repository: PostCategoryRepository,
    private val schoolRepository: SchoolRepository
) : PostCategoryService {
    override fun getCategories(schoolId: String): List<PostCategoryResponse> = repository
        .findAllBySchool(getSchoolById(schoolId), Sort.by("created_at").descending())
        .map { it.response() }

    override fun saveCategory(schoolId: String, request: PostCategoryRequest): PostCategoryResponse {
        val school = getSchoolById(schoolId)
        val category = converter.postCategoryRequestToPostCategory(request).copy(school = school)
        return repository.insert(category).response()
    }

    override fun showCategory(id: String): PostCategoryResponse = repository.findById(id).map {
        it.response()
    }.orElseThrow {
        PostCategoryNotFoundException("Post category not found")
    }

    override fun updateCategory(id: String, request: PostCategoryRequest): PostCategoryResponse = repository
        .findById(id).map { postCategory ->
            val category = converter.postCategoryRequestToPostCategory(postCategory, request).copy(
                updatedAt = Date.from(Instant.now())
            )
            repository.save(category).response()
        }.orElseThrow {
            PostCategoryNotFoundException("Post category not found")
        }

    private fun getSchoolById(id: String): School = schoolRepository.findById(id).orElseThrow {
        SchoolNotFoundException("School with id $id is not found")
    }
}
