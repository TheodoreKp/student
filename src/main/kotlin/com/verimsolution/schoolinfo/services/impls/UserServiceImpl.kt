package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.schoolinfo.entities.Role
import com.verimsolution.schoolinfo.entities.User
import com.verimsolution.schoolinfo.exceptions.PasswordNotMatchException
import com.verimsolution.schoolinfo.exceptions.RoleNotFoundException
import com.verimsolution.schoolinfo.exceptions.UserNotfoundException
import com.verimsolution.schoolinfo.repositories.RoleRepository
import com.verimsolution.schoolinfo.repositories.UserRepository
import com.verimsolution.schoolinfo.requests.ForgotPasswordRequest
import com.verimsolution.schoolinfo.requests.LoginRequest
import com.verimsolution.schoolinfo.requests.UserRequest
import com.verimsolution.schoolinfo.responses.JwtResponse
import com.verimsolution.schoolinfo.responses.UserResponse
import com.verimsolution.schoolinfo.services.UserService
import com.verimsolution.schoolinfo.utils.AppConverter
import com.verimsolution.schoolinfo.utils.JwtUtils
import com.verimsolution.schoolinfo.utils.UserPrincipal
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.stereotype.Service

@Primary
@Service
class UserServiceImpl(
    private val utils: JwtUtils,
    private val converter: AppConverter,
    private val repository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun refreshToken(token: String, jwtAuthProvider: JwtAuthenticationProvider): JwtResponse =
        jwtAuthProvider.authenticate(BearerTokenAuthenticationToken(token)).let {
            utils.getJwtToken(it)
        }

    override fun authUser(authentication: Authentication): UserResponse = repository
        .findByEmail(authentication.name).map {
            converter.userToUserResponse(it)
        }.orElseThrow {
            UserNotfoundException("User not found.")
        }

    override fun forgotPassword(request: ForgotPasswordRequest): UserResponse =
        repository.findByEmail(request.email!!).map {
            if (!request.password.equals(request.confirmPassword)) {
                throw PasswordNotMatchException("Password not match")
            }
            it.password = passwordEncoder.encode(request.password)
            val user = repository.save(it)
            converter.userToUserResponse(user)
        }.orElseThrow {
            UserNotfoundException("User not found.")
        }

    override fun saveUser(request: UserRequest): UserResponse {
        if (request.password != request.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }
        request.password = passwordEncoder.encode(request.password)
        val user = converter.userRequestToUser(request)
        user.roles.add(getRole())
        return repository.insert(user).let {
            converter.userToUserResponse(it)
        }
    }

    override fun showUser(email: String): User = repository.findByEmail(email).orElseThrow {
        UserNotfoundException("User not found")
    }

    override fun loginUser(request: LoginRequest, authenticationProvider: DaoAuthenticationProvider): JwtResponse =
        authenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(request.email, request.password)
        ).let {
            utils.getJwtToken(it)
        }

    override fun loadUserByUsername(username: String): UserDetails = repository.findByEmail(username)
        .map { UserPrincipal(it) }.orElseThrow {
            UserNotfoundException("User not found")
        }

    private fun getRole(): Role = roleRepository.findByName("ROLE_ADMIN").orElseThrow {
        RoleNotFoundException("Role not found")
    }
}
