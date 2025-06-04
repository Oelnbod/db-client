import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords
import stringHandling.*
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import java.util.Base64
import javax.crypto.spec.SecretKeySpec

@kotlin.io.encoding.ExperimentalEncodingApi
//https://developer.android.com/privacy-and-security/cryptography#kotlin
fun main() {
    val originalText = "Hello Kotlin AES Encryption!"
    //val secretKey = generateAESKey(256)
    //println(secretKeyToString(secretKey))

    
    val encryptedData = aesEncrypt(originalText.toByteArray(), secKey)
    println(encryptedData)
    val decryptedData = aesDecrypt(encryptedData, secKey)
    val decryptedText = String(decryptedData)
    println(decryptedText)

//    var exit = false
//    while (!exit) {
//        println("\nplease enter a number that corresponds to your preferred choice: \n 1: display all passwords \n 2: search by website \n 3: Add a password \n 4: Delete a password \n 5: Exit")
//        val input = readLine()
//        when (input) {
//            "1" -> println(displayAsTable(listAllPasswords()))
//            "2" -> {
//                println("Enter website to search for:")
//                val website = readln()
//                println(displayAsTable(listSomePasswords(website)))
//            }
//
//            "3" -> {
//                println("Enter your website")
//                val website = readln()
//                println("Enter your username")
//                val username = readln()
//                println("Enter your password")
//                val password = readln()
//                println(addPasswords(website, username, password))
//            }
//
//            "4" -> {
//                println("Enter the website of the password that you want to delete")
//                val website = readln()
//                println("Are you sure? (Y/n)")
//                val approved = readln()
//                if (approved == "Y") {
//                    println(deletePasswords(website))
//                } else {
//                    println("Canceled")
//                }
//            }
//
//            "5" -> exit = true
//            else -> println("invalid option, please enter 1, 2, 3, 4 or 5 in numeric form")
//        }
//    }
//    println("----------- \n Exiting \n ----------- ")
}


//https://medium.com/@appdevinsights/implementation-of-aes-encryption-in-android-dca250525b4
//https://www.baeldung.com/kotlin/advanced-encryption-standard
//Base64.getEncoder().encodeToString()

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

const val secKey: String = "+aXfDHPFRCAGXI09mpB8a5Hu7UcmeTVp"