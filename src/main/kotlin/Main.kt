import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords
import java.util.Arrays

fun main() {
    val data = listAllPasswords()
    val idList: List<String> = (data.map { it.id.toString() })
    val websiteList: List<String> = data.map { it.website }
    val usernameList: List<String> = data.map { it.username }
    val passwordList: List<String> = data.map { it.password }
    //println(idList + websiteList + usernameList + passwordList)
    val fullList = idList.zip(websiteList.zip(usernameList.zip(passwordList)))
    val fullArray: Array<Array<String>> = fullList.map {
        arrayOf(it.first, it.second.first, it.second.second.first, it.second.second.second)
    }.toTypedArray()
    println("[id, website, username, password]")

    for (items in fullArray) {
        val stringItem = items.contentDeepToString() //converts to a string
        val item = stringItem.replace(",", "  |  ") //formatting into table
        //this removes the brackets
        val endPoint = item.length-1
        println(item.substring(1, endPoint))


    }





//    println(addPasswords("p.p", "uid", "pwd"))
//    println(listAllPasswords())
//    println(listSomePasswords("example.com"))
//    println(deletePasswords("p.p"))
//    println(listAllPasswords())
}

