package com.verimsolution.schoolinfo.entities

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Document(collection = "classrooms")
data class Classroom(
    @Id
    val id: ObjectId,
    @Indexed(unique = true)
    val name: String,
    val reference: String,
    val description: String,
    val teacher: String,
    @DBRef
    val school: School,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        name = "",
        reference = "",
        description = "",
        teacher = "",
        school = School(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
