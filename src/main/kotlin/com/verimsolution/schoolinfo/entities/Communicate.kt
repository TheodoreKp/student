package com.verimsolution.schoolinfo.entities

import com.verimsolution.schoolinfo.utils.emuns.CommunicateType
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
@Document(collection = "communicates")
data class Communicate(
    @Id
    val id: ObjectId,
    val title: String,
    val content: String,
    val view: Int,
    @DBRef
    val classrooms: MutableCollection<Classroom>,
    @DBRef
    val professor: Professor?,
    val type: CommunicateType,
    val valid: Boolean,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) : Serializable {
    constructor() : this(
        id = ObjectId.get(),
        title = "",
        content = "",
        view = 0,
        mutableListOf(),
        professor = null,
        type = CommunicateType.GLOBAL,
        valid = false,
        createdAt = Date(),
        updatedAt = Date()
    )
}
