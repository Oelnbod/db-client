package stringHandling

import api.Password
import encrytion.aesDecrypt
import encrytion.secKey
import java.io.BufferedReader
import java.io.File

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
            var stringItem: String
            if (row.indexOf(item) == 3) {
                //this allows for decrypting passwords (as passwords are only encrypted)
                try {
                    val shortenedItem = item.take(item.length - 1) //removes final character (a \ is added somewhere)
                    val decryptedData = String(aesDecrypt(shortenedItem, secKey))
                    stringItem = padding(decryptedData, paddingLimit)
                } catch (_: Exception) {
                    //also catches column title
                    stringItem = padding(item, paddingLimit)
                }
            } else {
                //padding using limit above
                stringItem = padding(item, paddingLimit)
            }
            //inserting a pipe beforehand
            stringOut = "$stringOut| $stringItem  "


        }
        //adding a pipe to the end of each row and a blank line
        stringOut = "$stringOut| \n"

    }
    return (stringOut)

}
fun readFromFile(file: String): String {
    //this is for reading the key
    val bufferedReader: BufferedReader = File("src/main/resources/$file").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return (inputString)
}
fun writeToFile(file: String, text: String) {
    File("src/main/resources/$file").writeText(text)
}