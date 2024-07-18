package com.verimsolution.schoolinfo.entities

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.Instant

@Data
@Document(collection = "roles")
data class Role(
    val id: ObjectId = ObjectId(),
    val name: String,
    val createAt: Instant = Instant.now()
) : Serializable
