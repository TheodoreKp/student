package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.SchoolRequest
import com.verimsolution.schoolinfo.responses.SchoolResponse

interface SchoolService {
    fun getSchools(): List<SchoolResponse>

    fun saveSchool(schoolRequest: SchoolRequest): SchoolResponse

    fun showSchool(id: String): SchoolResponse

    fun updateSchool(id: String, schoolRequest: SchoolRequest): SchoolResponse
}