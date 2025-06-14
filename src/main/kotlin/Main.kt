import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords
import encrytion.aesEncrypt
import encrytion.generateAESKey
import encrytion.secKey
import encrytion.secretKeyToString
import stringHandling.displayAsTable
import stringHandling.writeToFile
import java.io.BufferedReader
import java.io.File


fun main() {

    //val secretKey = generateAESKey(256)
    //println(secretKeyToString(secretKey))


    var exit = false
    while (!exit) {
        println("\n please enter a number that corresponds to your preferred choice: \n 1: display all passwords \n 2: search by website \n 3: Add a password \n 4: Delete a password \n 5: reset encryption key \n 6: Exit")
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
                if (approved == "Y" || approved == "y") {
                    println(deletePasswords(website))
                } else {
                    println("Canceled")
                }
            }

            "5" -> {
                println("This is a quick way to ensure that all encrypted passwords will be unreadable for security reasons. \n It will delete the encryption key and regenerate it. It will not decrypt and reencrypt your passwords. \n\n It is recommended to perform this on initial setup, to ensure that your data is using a key not available on the internet. \n \n  Are you sure that you want to continue? (Y/n)")
                val approved = readln()
                if (approved == "Y" || approved == "y") {
                    val key = secretKeyToString(generateAESKey())
                    writeToFile("key.txt", key)
                    println("Key reset")
                } else {
                    println("Cancelled")
                }


            }

            "6" -> exit = true
            else -> println("invalid option, please enter 1, 2, 3, 4 or 5 in numeric form")
        }
    }
    println("----------- \n Exiting \n ----------- ")
}


