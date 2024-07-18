package com.verimsolution.schoolinfo.entities

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable


@Data
@Document(collection = "users")
data class User(
    @Id
    val id: ObjectId = ObjectId.get(),
    val username: String,
    var password: String,
    @Indexed(unique = true)
    val email: String,
    @Indexed(unique = true)
    val phone: String,
    val verify: Boolean = false,
    val emailVerify: Boolean = false,
    @DBRef
    val roles: MutableCollection<Role> = mutableListOf(),
    @DBRef
    val schools: MutableCollection<UserSchool> = mutableListOf(),
) : Serializable {
    constructor() : this(
        ObjectId.get(),
        "",
        "",
        "",
        "",
        false,
        false,
        mutableListOf(),
        mutableListOf()
    )
}

