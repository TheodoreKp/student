package com.verimsolution.schoolinfo.utils

import com.verimsolution.schoolinfo.entities.Professor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class ProfessorPrincipal(private val professor: Professor) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = professor.roles.map {
        SimpleGrantedAuthority(it.name)
    }.toMutableList()

    override fun getPassword(): String = professor.password

    override fun getUsername(): String = professor.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = professor.isNotLocked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = professor.verify
}
