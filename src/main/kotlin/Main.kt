import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


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

// this will list convert a json string into a table of passwords
fun stringToJson(json: String): List<Password> {
    //deserializing the json value
    val listPasswords = Json.decodeFromString<List<Password>>(json)
    return(listPasswords)

}
// this queries the api with the required uri, with error handling
fun queryAPI(url: String): String {
    val client = HttpClient.newBuilder().build()
    try {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return(response.body())
    } catch (e: Exception) {
        System.err.println("" +
                "-----------------------------------------------------\n" +
                "                        :(\n" +
                "Server has crashed ($e)\n" +
                "Please reboot the password server\n" +
                "-----------------------------------------------------")
        return("")
    }

}

const val socketWithPrefix: String = "http://192.168.1.120:7878"
const val key: String = "seckey"