package com.verimsolution.schoolinfo.config.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider

@Configuration
class SecurityEncoder(
    private val keyUtils: KeyUtils,
    private val converter: JwtAuthenticationConverter
) {

    @Bean
    @Primary
    fun jwtAccessTokenDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyUtils.accessTokenPublicKey).build()
    }

    @Bean
    @Primary
    fun jwtAccessTokenEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(keyUtils.accessTokenPublicKey)
            .privateKey(keyUtils.accessTokenPrivateKey).build()
        val jwkSource: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    fun jwtRefreshTokenDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyUtils.refreshTokenPublicKey).build()
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    fun jwtRefreshTokenEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(keyUtils.refreshTokenPublicKey)
            .privateKey(keyUtils.refreshTokenPrivateKey)
            .build()
        val jwkSource: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    @Qualifier("JwtRefreshAuthenticationProvider")
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider {
        val provider = JwtAuthenticationProvider(jwtRefreshTokenDecoder())
        provider.setJwtAuthenticationConverter(converter)
        return provider
    }
}
