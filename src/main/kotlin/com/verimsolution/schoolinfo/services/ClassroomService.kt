package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.ClassroomRequest
import com.verimsolution.schoolinfo.responses.ClassroomResponse

interface ClassroomService {
    fun getClassroomsBySchool(schoolId: String): List<ClassroomResponse>

    fun saveClassroom(schoolId: String, request: ClassroomRequest): ClassroomResponse

    fun showClassroom(id: String): ClassroomResponse

    fun updateClassroom(id: String, request: ClassroomRequest): ClassroomResponse

    fun listClassroom(): List<ClassroomResponse>
}
