import kotlinx.serialization.Serializable

import api.queryAPI
import api.stringToJson

@Serializable
data class Password(val id: UInt, val website: String, val username: String, val password: String)

fun main() {
    println(listAllPasswords())
    println(listSomePasswords("example.com"))

}

//returns passwords based on a website search
fun listSomePasswords(website: String): List<Password> {
    val query = "$socketWithPrefix/$key/list_row/$website"
    val response = queryAPI(query)
    val tablePasswords = stringToJson(response)
    return(tablePasswords)

}

//this will return all passwords in the database
fun listAllPasswords(): List<Password> {
    val response = queryAPI("$socketWithPrefix/$key/list_all/")
    val tablePasswords = stringToJson(response)
    return(tablePasswords)
}



const val socketWithPrefix: String = "http://192.168.1.120:7878"
const val key: String = "seckey"