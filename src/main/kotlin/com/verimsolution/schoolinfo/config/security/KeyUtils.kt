package com.verimsolution.schoolinfo.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class KeyUtils(
    var environment: Environment,
    @Value("\${access-token.private}")
    private val accessTokenPrivateKeyPath: String,
    @Value("\${access-token.public}")
    private val accessTokenPublicKeyPath: String,
    @Value("\${refresh-token.private}")
    private val refreshTokenPrivateKeyPath: String,
    @Value("\${refresh-token.public}")
    private val refreshTokenPublicKeyPath: String,
) {

    private var _accessTokenKeyPair: KeyPair? = null
    private var _refreshTokenKeyPair: KeyPair? = null

    private val accessTokenKeyPair: KeyPair
        get() {
            if (Objects.isNull(_accessTokenKeyPair)) {
                _accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath)
            }
            return _accessTokenKeyPair!!
        }

    private val refreshTokenKeyPair: KeyPair
        get() {
            if (Objects.isNull(_refreshTokenKeyPair)) {
                _refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath)
            }
            return _refreshTokenKeyPair!!
        }

    private fun getKeyPair(publicKeyPath: String, privateKeyPath: String): KeyPair {
        var keyPair: KeyPair? = null

        val publicKeyFile = File(publicKeyPath)
        val privateKeyFile = File(privateKeyPath)
        if (privateKeyFile.exists() && publicKeyFile.exists()) {
            try {
                val keyFactory = KeyFactory.getInstance("RSA")

                val publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath())
                val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
                val publicKey = keyFactory.generatePublic(publicKeySpec)

                val privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath())
                val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
                val privateKey = keyFactory.generatePrivate(privateKeySpec)

                keyPair = KeyPair(publicKey, privateKey)
                return keyPair
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            if (listOf(*environment.activeProfiles).contains("prod")) {
                throw RuntimeException("Public and Private key does not exist")
            }
        }

        val directory = File("access-refresh-token-keys")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPair = keyPairGenerator.generateKeyPair()
            FileOutputStream(publicKeyPath).use { fos ->
                val keySpec = X509EncodedKeySpec(keyPair.public.encoded)
                fos.write(keySpec.encoded)
            }

            FileOutputStream(privateKeyPath).use { fos ->
                val keySpec = PKCS8EncodedKeySpec(keyPair!!.private.encoded)
                fos.write(keySpec.encoded)
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }

        return keyPair!!
    }

    val accessTokenPublicKey: RSAPublicKey
        get() = accessTokenKeyPair.public as RSAPublicKey
    val accessTokenPrivateKey: RSAPrivateKey
        get() = accessTokenKeyPair.private as RSAPrivateKey
    val refreshTokenPublicKey: RSAPublicKey
        get() = refreshTokenKeyPair.public as RSAPublicKey
    val refreshTokenPrivateKey: RSAPrivateKey
        get() = refreshTokenKeyPair.private as RSAPrivateKey
}