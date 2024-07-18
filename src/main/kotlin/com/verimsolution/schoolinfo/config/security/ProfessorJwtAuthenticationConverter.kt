package com.verimsolution.schoolinfo.config.security

import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.utils.ProfessorPrincipal
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class ProfessorJwtAuthenticationConverter(
    private val repository: ProfessorRepository
) : Converter<Jwt, UsernamePasswordAuthenticationToken> {
    override fun convert(source: Jwt): UsernamePasswordAuthenticationToken? = repository
        .findByUsername(source.subject).map { ProfessorPrincipal(it) }
        .map { UsernamePasswordAuthenticationToken(it, source, it.authorities) }
        .orElseThrow { StudentNotFoundException("Student not found with ${source.subject} AS") }
}
