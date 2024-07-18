package com.verimsolution.schoolinfo.responses

import com.verimsolution.schoolinfo.entities.PostCategory
import com.verimsolution.schoolinfo.entities.School
import com.verimsolution.schoolinfo.entities.User
import org.bson.types.ObjectId
import java.util.*

data class PostResponse(
    val id: String,
    val title: String,
    val introduction: String,
    val content: String,
    val image: String,
    val user: UserResponse,
    val school: SchoolResponse,
    val view: Int,
    val category: PostCategoryResponse,
    val createdAt: Date,
    val updatedAt: Date
) {
    constructor() : this(
        id = "",
        title = "",
        introduction = "",
        content = "",
        image = "",
        user = UserResponse(),
        school = SchoolResponse(),
        view = 0,
        category = PostCategoryResponse(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
