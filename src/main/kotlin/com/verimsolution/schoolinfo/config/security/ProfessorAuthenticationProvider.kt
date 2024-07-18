package com.verimsolution.schoolinfo.config.security

import com.verimsolution.schoolinfo.services.ProfessorService
import com.verimsolution.schoolinfo.utils.checkAuthentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ProfessorAuthenticationProvider(
    private val service: ProfessorService,
    private val passwordEncoder: PasswordEncoder
) : AbstractUserDetailsAuthenticationProvider() {
    override fun additionalAuthenticationChecks(
        userDetails: UserDetails, authentication: UsernamePasswordAuthenticationToken
    ) {
        checkAuthentication(userDetails, authentication, passwordEncoder, messages, logger)
    }

    override fun retrieveUser(username: String, authentication: UsernamePasswordAuthenticationToken?): UserDetails =
        service.loadUserByUsername(username)
}
