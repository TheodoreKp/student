package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(collection = "professors")
data class Professor(
    @Id
    val id: ObjectId = ObjectId.get(),
    val firstName: String,
    val lastName: String,
    val username: String,
    var password: String,
    val reference: String,
    @Indexed(unique = true)
    val email: String,
    @Indexed(unique = true)
    val phone: String,
    val genre: String,
    val verify: Boolean = false,
    val isNotLocked: Boolean = true,
    val emailVerify: Boolean = false,
    val avatar: String? = null,
    @DBRef
    val roles: MutableCollection<Role>,
    @DBRef
    val classrooms: MutableCollection<Classroom>,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) {
    constructor() : this(
        id = ObjectId.get(),
        firstName = "",
        lastName = "",
        username = "",
        password = "",
        reference = "",
        email = "",
        phone = "",
        genre = "",
        verify = false,
        isNotLocked = true,
        emailVerify = false,
        avatar = null,
        roles = mutableListOf(),
        classrooms = mutableListOf(),
        createdAt = Date(),
        updatedAt = Date()
    )
}
