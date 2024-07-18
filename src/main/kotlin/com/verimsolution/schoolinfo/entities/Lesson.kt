package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(collection = "lessons")
data class Lesson(
    @Id
    val id: ObjectId,
    val reference: String,
    val name: String,
    val description: String,
    val cover: String?,
    val subject: String,
    @DBRef
    val classroom: Classroom,
    @DBRef
    val professor: Professor,
    val files: MutableCollection<String>,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) {
    constructor() : this(
        id = ObjectId.get(),
        reference = "",
        name = "",
        cover = "",
        subject = "",
        description = "",
        classroom = Classroom(),
        professor = Professor(),
        files = mutableListOf(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
