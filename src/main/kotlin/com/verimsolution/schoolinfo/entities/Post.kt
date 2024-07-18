package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Document(collection = "posts")
data class Post(
    @Id
    val id: ObjectId,
    val title: String,
    val introduction: String,
    val content: String,
    val image: String,
    @DBRef
    val user: User,
    @DBRef
    val school: School,
    val view: Int = 0,
    @Field("category")
    val category: PostCategory,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        title = "",
        introduction = "",
        content = "",
        image = "",
        user = User(),
        school = School(),
        createdAt = Date(),
        updatedAt = Date(),
        view = 0,
        category = PostCategory()
    )
}
