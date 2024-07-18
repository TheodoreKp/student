package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Document(collection = "forum_responses")
data class Response(
    @Id
    val id: ObjectId,
    val description: String,
    @DBRef
    val professor: Professor?,
    @DBRef
    val student: Student?,
    val solution: Boolean,
    @DBRef
    val forum: Forum,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        description = "",
        professor = null,
        student = null,
        solution = false,
        forum = Forum(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
