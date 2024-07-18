package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Document(collection = "post_categories")
data class PostCategory(
    @field:Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    @field:DBRef
    val school: School,
    @field:Field("created_at")
    val createdAt: Date,
    @field:Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(id = ObjectId.get(), name = "", school = School(), createdAt = Date(), updatedAt = Date())
}
