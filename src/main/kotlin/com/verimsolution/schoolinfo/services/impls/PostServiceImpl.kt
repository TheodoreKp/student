package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.entities.PostCategory
import com.verimsolution.schoolinfo.entities.School
import com.verimsolution.schoolinfo.entities.User
import com.verimsolution.schoolinfo.exceptions.PostCategoryNotFoundException
import com.verimsolution.schoolinfo.exceptions.PostNotFoundException
import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.PostCategoryRepository
import com.verimsolution.schoolinfo.repositories.PostRepository
import com.verimsolution.schoolinfo.repositories.SchoolRepository
import com.verimsolution.schoolinfo.repositories.UserRepository
import com.verimsolution.schoolinfo.requests.PostRequest
import com.verimsolution.schoolinfo.responses.PostResponse
import com.verimsolution.schoolinfo.services.FileService
import com.verimsolution.schoolinfo.services.PostService
import com.verimsolution.schoolinfo.utils.AppConverter
import com.verimsolution.schoolinfo.utils.response
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceImpl(
    private val converter: AppConverter,
    private val fileService: FileService,
    private val repository: PostRepository,
    private val userRepository: UserRepository,
    private val schoolRepository: SchoolRepository,
    private val postCategoryRepository: PostCategoryRepository
) : PostService {
    override fun getPosts(schoolId: String) = repository
        .findAllBySchool(getSchoolById(schoolId), Sort.by("created_at").descending())
        .map { it.response() }

    override fun storePost(schoolId: String, request: PostRequest): PostResponse {
        val school = getSchoolById(schoolId)
        if (request.image == null) {
            throw IllegalArgumentException("Image is required")
        }

        val post = converter.postRequestToPost(request).copy(
            school = school,
            image = fileService.saveFile(request.image, "post_${UUID.randomUUID()}", "post/"),
            category = getPostCategoryById(request.categoryId!!),
            user = getAuthUser()
        )
        return repository.insert(post).response()
    }

    override fun showPost(id: String): PostResponse = repository.findById(id).map {
        it.response()
    }.orElseThrow {
        PostNotFoundException("Post not found")
    }

    override fun updatePost(id: String, request: PostRequest): PostResponse = repository.findById(id).map { post ->
        val image: String = if (request.image != null) {
            fileService.deleteFile(post.image)
            fileService.saveFile(request.image, "post_${UUID.randomUUID()}", "post/")
        } else {
            post.image
        }
        val data = converter.postRequestToPost(post, request).copy(
            category = getPostCategoryById(request.categoryId!!),
            image = image,
        )
        repository.save(data).response()
    }.orElseThrow {
        PostNotFoundException("Post not found")
    }

    override fun listAllPost(): List<PostResponse> = repository.findAll(Sort.by("created_at").descending())
        .map { it.response() }

    override fun limitedPost(limit: Int): List<PostResponse> = repository
        .findAll(Sort.by("created_at").descending())
        .take(limit).map { it.response() }

    private fun getSchoolById(id: String): School = schoolRepository.findById(id).orElseThrow {
        SchoolNotFoundException("School not found")
    }

    private fun getPostCategoryById(id: String): PostCategory = postCategoryRepository.findById(id).orElseThrow {
        PostCategoryNotFoundException("Post category not found")
    }

    private fun getAuthUser(): User =
        userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name).orElseThrow {
            UserNotfoundException("User not found")
        }
}
