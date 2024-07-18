package com.verimsolution.schoolinfo.utils

import com.verimsolution.schoolinfo.responses.JwtResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit


@Component
class JwtUtils(
    private val accessTokenEncoder: JwtEncoder,
    @Qualifier("jwtRefreshTokenEncoder")
    private val refreshTokenEncoder: JwtEncoder
) {

    private fun createAccessToken(authentication: Authentication): String {
        val user = authentication.principal as UserDetails
        val now = Instant.now()
        val claims = getClaimsFromUser(user)
        val claimsSet = JwtClaimsSet.builder()
            .issuer("keyApp")
            .issuedAt(now)
            .expiresAt(now.plus(5, ChronoUnit.DAYS))
            .subject(user.username)
            .claim(AUTHORITIES, claims)
            .build()
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    private fun createRefreshToken(authentication: Authentication): String {
        val user = authentication.principal as UserDetails
        val now = Instant.now()
        val claims = getClaimsFromUser(user)
        val claimsSet = JwtClaimsSet.builder()
            .issuer("keyApp")
            .issuedAt(now)
            .expiresAt(now.plus(30, ChronoUnit.DAYS))
            .subject(user.username)
            .claim(AUTHORITIES, claims)
            .build()
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    private fun getClaimsFromUser(user: UserDetails): Array<String> = user.authorities
        .map { obj: GrantedAuthority -> obj.authority }
        .toTypedArray()

    fun getJwtToken(authentication: Authentication): JwtResponse {
        if (authentication.principal !is UserDetails) {
            throw BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect")
        }
        val refreshToken: String = if (authentication.credentials is Jwt) {
            val now = Instant.now()
            val expireAt = (authentication.credentials as Jwt).expiresAt!!
            val duration = Duration.between(now, expireAt)
            val dayUntilExpired = duration.toDays()
            if (dayUntilExpired < 7) {
                createRefreshToken(authentication)
            } else {
                (authentication.credentials as Jwt).tokenValue
            }
        } else {
            createRefreshToken(authentication)
        }
        return JwtResponse(
            accessToken = createAccessToken(authentication),
            refreshToken = refreshToken
        )
    }

    fun validateToken(jwt: String): Boolean = try {
        audienceValidator().validate(Jwt.withTokenValue(jwt).build())
        true
    } catch (e: Exception) {
        false
    }


    fun audienceValidator(): OAuth2TokenValidator<Jwt> {
        return AudienceValidator()
    }

    internal class AudienceValidator : OAuth2TokenValidator<Jwt> {
        var error: OAuth2Error = OAuth2Error("custom_code", "Custom error message", null)

        override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
            return if (jwt.audience.contains("messaging")) {
                OAuth2TokenValidatorResult.success()
            } else {
                OAuth2TokenValidatorResult.failure(error)
            }
        }
    }
}