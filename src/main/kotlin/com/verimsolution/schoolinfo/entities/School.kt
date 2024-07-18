package com.verimsolution.schoolinfo.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schools")
data class School(
    @field:Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val address: String,
    @field:Indexed(unique = true)
    val phone: String,
    @field:Indexed(unique = true)
    val email: String,
    val principal: String
) : Serializable {

    constructor() : this(ObjectId.get(), "", "", "", "", "")
}