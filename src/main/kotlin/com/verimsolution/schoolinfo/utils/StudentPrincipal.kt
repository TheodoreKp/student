package com.verimsolution.schoolinfo.utils

import com.verimsolution.schoolinfo.entities.Student
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class StudentPrincipal(private val student: Student) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = student.roles.map {
        SimpleGrantedAuthority(it.name)
    }.toMutableList()

    override fun getPassword(): String = student.password

    override fun getUsername(): String = student.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = student.isNotLocked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = student.verify

}
