package com.verimsolution.schoolinfo.config.security

import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.utils.StudentPrincipal
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class StudentJwtAuthenticationConverter(
    private val repository: StudentRepository
): Converter<Jwt, UsernamePasswordAuthenticationToken> {
    override fun convert(source: Jwt): UsernamePasswordAuthenticationToken? = repository
        .findByUsername(source.subject).map { StudentPrincipal(it) }
        .map { UsernamePasswordAuthenticationToken(it, source, it.authorities) }
        .orElseThrow { StudentNotFoundException("Student not found with ${source.subject} AS") }
}
