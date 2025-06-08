import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords
import stringHandling.*
import encrytion.*


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

    var exit = false
    while (!exit) {
        println("\n please enter a number that corresponds to your preferred choice: \n 1: display all passwords \n 2: search by website \n 3: Add a password \n 4: Delete a password \n 5: Exit")
        val input = readLine()
        when (input) {
            "1" -> println(displayAsTable(listAllPasswords()))
            "2" -> {
                println("Enter website to search for:")
                val website = readln()
                println(displayAsTable(listSomePasswords(website)))
            }

            "3" -> {
                println("Enter your website")
                val website = readln()
                println("Enter your username")
                val username = readln()
                println("Enter your password")
                val password = readln()
                val encryptedPassword = aesEncrypt(password.toByteArray(), secKey)
                println(encryptedPassword)
                println(addPasswords(website, username, encryptedPassword))
            }

            "4" -> {
                println("Enter the website of the password that you want to delete")
                val website = readln()
                println("Are you sure? (Y/n)")
                val approved = readln()
                if (approved == "Y") {
                    println(deletePasswords(website))
                } else {
                    println("Canceled")
                }
            }

            "5" -> exit = true
            else -> println("invalid option, please enter 1, 2, 3, 4 or 5 in numeric form")
        }
    }
    println("----------- \n Exiting \n ----------- ")
}


const val secKey: String = "+aXfDHPFRCAGXI09mpB8a5Hu7UcmeTVp"