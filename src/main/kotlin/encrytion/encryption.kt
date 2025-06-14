package encrytion


import stringHandling.readFromFile
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

//reading key from keyfile
val secKey: String = readFromFile("key.txt")

fun generateAESKey(keySize: Int = 256): SecretKey {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(keySize)
    return keyGenerator.generateKey()
}

fun stringToSecretKey(keyString: String, algorithm: String): SecretKey {
    // Ensure the key string is hashed to a fixed size
    val keyBytes = keyString.toByteArray()
    val keySpec = SecretKeySpec(keyBytes, algorithm)
    return keySpec
}

fun aesEncrypt(data: ByteArray, secretKeyText: String): String {
    val secretKey = stringToSecretKey(secretKeyText, "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val iv = ByteArray(16) // 128-bit IV
    SecureRandom().nextBytes(iv)
    val ivParameterSpec = IvParameterSpec(iv)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)

    val encryptedData = cipher.doFinal(data)
    val encodedIv = Base64.getEncoder().encodeToString(iv)
    val encodedEncryptedData = Base64.getEncoder().encodeToString(encryptedData)

    return "$encodedIv:$encodedEncryptedData"
}

fun aesDecrypt(encryptedData: String, secretKeyText: String): ByteArray {
    val secretKey = stringToSecretKey(secretKeyText, "AES")
    val parts = encryptedData.split(":")
    if (parts.size != 2) {
        throw IllegalArgumentException("Invalid encrypted data format")
    }

    val encodedIv = parts[0]
    val encodedEncryptedData = parts[1]

    val iv = Base64.getDecoder().decode(encodedIv)
    val encryptedBytes = Base64.getDecoder().decode(encodedEncryptedData)

    val ivParameterSpec = IvParameterSpec(iv)
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)

    return cipher.doFinal(encryptedBytes)
}

fun secretKeyToString(secretKey: SecretKey): String {
    val encodedBytes = secretKey.encoded
    val base64EncodedString = Base64.getEncoder().encodeToString(encodedBytes)
    return base64EncodedString
}


