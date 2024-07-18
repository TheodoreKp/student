package com.verimsolution.schoolinfo.config.security

import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.UserRepository
import com.verimsolution.schoolinfo.utils.UserPrincipal
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationConverter(
    private val repository: UserRepository
): Converter<Jwt, UsernamePasswordAuthenticationToken> {

    override fun convert(source: Jwt): UsernamePasswordAuthenticationToken? =
        repository.findByEmail(source.subject).map { UserPrincipal(it) }
            .map { UsernamePasswordAuthenticationToken(it, source, it.authorities) }
            .orElseThrow { UserNotfoundException("User not found") }
}