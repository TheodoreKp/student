package com.verimsolution.schoolinfo.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.util.*

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "forums")
data class Forum(
    @Id
    val id: ObjectId,
    val title: String,
    val description: String,
    @DBRef
    val professor: Professor?,
    @DBRef
    val student: Student?,
    val views: Int,
    @DBRef
    val classroom: Classroom,
    val responses: Long,
    val solve: Boolean,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        title = "",
        description = "",
        professor = null,
        student = null,
        views = 0,
        classroom = Classroom(),
        responses = 0,
        createdAt = Date(),
        updatedAt = Date(),
        solve = false
    )
}
