package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.school.exceptions.SchoolNotFoundException
import com.verimsolution.schoolinfo.entities.User
import com.verimsolution.schoolinfo.entities.UserSchool
import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.SchoolRepository
import com.verimsolution.schoolinfo.repositories.UserRepository
import com.verimsolution.schoolinfo.repositories.UserSchoolRepository
import com.verimsolution.schoolinfo.requests.SchoolRequest
import com.verimsolution.schoolinfo.responses.SchoolResponse
import com.verimsolution.schoolinfo.services.SchoolService
import com.verimsolution.schoolinfo.utils.AppConverter
import lombok.AllArgsConstructor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class SchoolServiceImpl(
    private val converter: AppConverter,
    private val repository: SchoolRepository,
    private val userRepository: UserRepository,
    private val userSchoolRepository: UserSchoolRepository
) : SchoolService {

    override fun getSchools(): List<SchoolResponse> = getUserPrincipal().schools.map {
        converter.schoolToSchoolResponse(it.school)
    }

    override fun saveSchool(schoolRequest: SchoolRequest): SchoolResponse {
        val user = getUserPrincipal()
        val school = converter.schoolRequestToSchool(schoolRequest)
        val savedSchool = repository.insert(school)
        val userSchool = UserSchool(school = school, enable = true)
        userSchoolRepository.insert(userSchool).let {
            user.schools.add(it)
            userRepository.save(user)
        }
        return converter.schoolToSchoolResponse(savedSchool)
    }

    override fun showSchool(id: String): SchoolResponse = repository.findById(id).map {
        converter.schoolToSchoolResponse(it)
    }.orElseThrow {
        SchoolNotFoundException("School not found")
    }

    override fun updateSchool(id: String, schoolRequest: SchoolRequest): SchoolResponse = repository.findById(id).map {
        val school = converter.schoolRequestToSchool(it, schoolRequest)
        repository.save(school)
    }.map {
        converter.schoolToSchoolResponse(it)
    }.orElseThrow {
        SchoolNotFoundException("School not found")
    }

    private fun getUserPrincipal(): User = userRepository
        .findByEmail(SecurityContextHolder.getContext().authentication.name)
        .orElseThrow { UserNotfoundException("User not found") }

}
