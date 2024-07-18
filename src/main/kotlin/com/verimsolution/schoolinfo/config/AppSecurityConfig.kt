package com.verimsolution.schoolinfo.config

import com.verimsolution.schoolinfo.config.security.JwtAuthenticationConverter
import com.verimsolution.schoolinfo.config.security.ProfessorJwtAuthenticationConverter
import com.verimsolution.schoolinfo.config.security.StudentJwtAuthenticationConverter
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class AppSecurityConfig(
    private val passwordEncoder: PasswordEncoder,
    private val converter: JwtAuthenticationConverter,
    private val userDetailsService: UserDetailsService,
    private val studentConverter: StudentJwtAuthenticationConverter,
    private val professorConverter: ProfessorJwtAuthenticationConverter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }.cors { }
            .securityMatcher("/school/**")
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    SCHOOL_API_BASE_URL + "register",
                    SCHOOL_API_BASE_URL + "login",
                    SCHOOL_API_BASE_URL + "forgot/password",
                    SCHOOL_API_BASE_URL + "refresh/token",
                    "/ws/**",
                ).permitAll().anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { jwt -> jwt.jwtAuthenticationConverter(converter) } }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling.accessDeniedHandler(BearerTokenAccessDeniedHandler())
                    .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    @Bean
    @Order(1)
    fun studentSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }.cors { }
            .securityMatcher("/student/**")
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    STUDENT_API_BASE_URL + "login", "/ws/**", STUDENT_API_BASE_URL + "register",
                    STUDENT_API_BASE_URL + "classrooms"
                ).permitAll().anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { jwt -> jwt.jwtAuthenticationConverter(studentConverter) } }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(BearerTokenAccessDeniedHandler())
                    .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    @Bean
    @Order(2)
    fun professorSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }.cors { }
            .securityMatcher("/professor/**")
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    PROFESSOR_API_BASE_URL + "login", "/ws/**", PROFESSOR_API_BASE_URL + "register",
                ).permitAll().anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { jwt -> jwt.jwtAuthenticationConverter(professorConverter) } }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(BearerTokenAccessDeniedHandler())
                    .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(userDetailsService)
        return provider
    }
}
