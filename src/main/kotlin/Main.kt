import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords
import stringHandling.*
fun main() {


    var exit = false
    while (!exit) {
        println("\nplease enter a number that corresponds to your preferred choice: \n 1: display all passwords \n 2: search by website \n 3: Add a password \n 4: Delete a password \n 5: Exit")
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
                println(addPasswords(website, username, password))
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

