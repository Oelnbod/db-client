package api
//this package is an abstraction layer that allows for commands to be run that generates a query and runs it.

fun deletePasswords(website: String): String {
    val url = "$socketWithPrefix/$key/delete/$website"
    val response = queryAPI(url)
    return (response)
}

fun addPasswords(website: String, username: String, password: String): String {
    val url = "$socketWithPrefix/$key/add/$website,$username,$password/"
    val response = queryAPI(url)
    return (response)
}

//returns passwords based on a website search
fun listSomePasswords(website: String): List<Password> {
    val url = "$socketWithPrefix/$key/list_row/$website"
    val response = queryAPI(url)
    val tablePasswords = stringToJson(response)
    return (tablePasswords)

}

//this will return all passwords in the database
fun listAllPasswords(): List<Password> {
    val response = queryAPI("$socketWithPrefix/$key/list_all/")
    val tablePasswords = stringToJson(response)
    return (tablePasswords)
}


const val socketWithPrefix: String = "http://192.168.1.120:7878"
const val key: String = "seckey"