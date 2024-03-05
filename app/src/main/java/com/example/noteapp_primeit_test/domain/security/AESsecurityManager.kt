package com.example.noteapp_primeit_test.domain.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject







class AESecurityManager @Inject constructor(){

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val KEY_ALIAS = "myKeyAlias"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val IV_SIZE = 12
        private const val TAG_SIZE = 128
    }

    private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }

    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        val encryptedWithIv = iv + encrypted
        return Base64.getEncoder().encodeToString(encryptedWithIv)
    }

    fun decrypt(encryptedInput: String): String {
        val encryptedBytesWithIv = Base64.getDecoder().decode(encryptedInput)
        val iv = encryptedBytesWithIv.copyOfRange(0, IV_SIZE)
        val encryptedBytes = encryptedBytesWithIv.copyOfRange(IV_SIZE, encryptedBytesWithIv.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey
        val spec = GCMParameterSpec(TAG_SIZE, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decrypted = cipher.doFinal(encryptedBytes)
        return String(decrypted, Charsets.UTF_8)
    }
}


