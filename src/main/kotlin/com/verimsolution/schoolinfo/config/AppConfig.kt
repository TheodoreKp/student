package com.verimsolution.schoolinfo.config

import com.verimsolution.schoolinfo.entities.Role
import com.verimsolution.schoolinfo.entities.User
import com.verimsolution.schoolinfo.repositories.RoleRepository
import com.verimsolution.schoolinfo.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AppConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun runner(roleRepository: RoleRepository, userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner {
//            val roles = listOf(
//                Role(name = "ROLE_USER"),
//                Role(name = "ROLE_ADMIN"),
//                Role(name = "ROLE_SUPER_ADMIN"),
//                Role(name = "ROLE_TEACHER"),
//                Role(name = "ROLE_STUDENT"),
//            )
//            roleRepository.saveAll(roles);
//            val role = roleRepository.findByName("ROLE_SUPER_ADMIN").orElseThrow()
//            val user = User(
//                username = "admin",
//                password = passwordEncoder().encode("password"),
//                email = "admin@gmail.com",
//                phone = "123456789",
//                verify = true,
//                emailVerify = true,
//            )
//            user.roles.add(role)
//            userRepository.save(user)
        }
    }

}
