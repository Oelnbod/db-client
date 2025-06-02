import api.Password
import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords

fun main() {


    println("All passwords listed below: \n" + displayAsTable(listAllPasswords()))
    println("please enter a number that corresponds to your preferred choice: \n 1: display all passwords again \n 2: search by website \n 3: Add a password \n 4: Delete a password")
    val input = readLine()
    println(input)
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

        else -> println("invalid")
    }


}

fun largest(list: List<String>): Int {
    //this works by adding to a list and then sorting, reversing and taking the first value
    val lengthsList = mutableListOf<Int>()
    for (items in list) {
        lengthsList.add(items.length)

    }
    return (lengthsList.sorted().reversed()[0])

}

fun padding(input: String, target: Int): String {
    //This is for a consistent length of column
    var input = input
    var suitableLength = false
    //println("TARGET:  $target")
    while (!suitableLength) {
        if (input.length == target) {
            suitableLength = true
        } else {
            input = "$input "
            //println(input)
        }
    }
    return (input)
}

fun displayAsTable(data: List<Password>): String {
    var stringOut = "" //this is to allow outputting to user as a string (useful for gui change)
    //parsing it to independent lists by column appending at the start is to give titles to column
    val idList: List<String> = listOf("Index:") + (data.map { it.id.toString() })
    val websiteList: List<String> = listOf("Website:") + data.map { it.website }
    val usernameList: List<String> = listOf("Usernames:") + data.map { it.username }
    val passwordList: List<String> = listOf("Passwords:") + data.map { it.password }

    //merging lists together through .zip
    val fullList = idList.zip(websiteList.zip(usernameList.zip(passwordList)))
    //converting to an array for convenience
    val fullArray: Array<Array<String>> = fullList.map {
        arrayOf(it.first, it.second.first, it.second.second.first, it.second.second.second)
    }.toTypedArray()

    //identifying length so can be referenced by index
    val lengthAll: List<Int> =
        listOf(largest(idList), largest(websiteList), largest(usernameList), largest(passwordList))
    for (row in fullArray) {
        //println()

        for (item in row) {
            //getting a limit for padding from list above
            val paddingLimit: Int = lengthAll[row.indexOf(item)]
            //padding using limit above
            val stringItem = padding(item, paddingLimit)

            //inserting a pipe beforehand
            stringOut = "$stringOut| $stringItem  "


        }
        //adding a pipe to the end of each row and a blank line
        stringOut = "$stringOut| \n"

    }
    return (stringOut)

}