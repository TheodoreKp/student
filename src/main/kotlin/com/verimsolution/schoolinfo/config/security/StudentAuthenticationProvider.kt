package com.verimsolution.schoolinfo.config.security

import com.verimsolution.schoolinfo.services.StudentService
import com.verimsolution.schoolinfo.utils.StudentPrincipal
import com.verimsolution.schoolinfo.utils.checkAuthentication
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class StudentAuthenticationProvider(
    private val service: StudentService,
    private val passwordEncoder: PasswordEncoder
) : AbstractUserDetailsAuthenticationProvider() {
    override fun additionalAuthenticationChecks(
        userDetails: UserDetails, authentication: UsernamePasswordAuthenticationToken
    ) {
        checkAuthentication(userDetails, authentication, passwordEncoder, messages, logger)
    }

    override fun retrieveUser(identifier: String, authentication: UsernamePasswordAuthenticationToken): UserDetails =
        service.loadUserByUsername(identifier)
}
