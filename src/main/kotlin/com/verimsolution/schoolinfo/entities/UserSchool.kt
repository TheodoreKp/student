package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable
import java.time.Instant

@Document(collection = "user_schools")
data class UserSchool(
    @Id
    val id: ObjectId = ObjectId.get(),
    @DBRef
    val school: School,
    val enable: Boolean,
    @Field("created_at")
    val createdAt: Instant = Instant.now(),
    @Field("updated_at")
    val updatedAt: Instant = Instant.now()
) : Serializable {
    constructor() : this(
        ObjectId.get(),
        School(),
        false,
        Instant.now(),
        Instant.now()
    )
}
