package com.verimsolution.schoolinfo.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
data class Student(
    @field:Id
    val id: ObjectId,
    val username: String,
    var password: String,
    @Indexed(unique = true)
    val reference: String,
    @Field("first_name")
    val firstName: String,
    @Field("last_name")
    val lastName: String,
    @Indexed(unique = true)
    val email: String,
    @Indexed(unique = true)
    val phone: String,
    val birthday: Date,
    val genre: String,
    @Field("image_url")
    val imageUrl: String?,
    @Field("classroom_id") @DBRef
    val classroom: Classroom,
    val roles: MutableCollection<Role>,
    val verify: Boolean,
    val isNotLocked: Boolean,
    val emailVerify: Boolean,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        username = "",
        reference = "",
        firstName = "",
        lastName = "",
        phone = "",
        genre = "",
        birthday = Date(),
        imageUrl = null,
        classroom = Classroom(),
        email = "",
        password = "",
        roles = mutableListOf(),
        verify = false,
        isNotLocked = true,
        emailVerify = false,
        createdAt = Date(),
        updatedAt = Date()
    )
}
