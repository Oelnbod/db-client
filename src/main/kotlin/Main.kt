import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val client = HttpClient.newBuilder().build()


fun main() {
    listPasswords()


}

@Serializable
data class Password(val id: UInt, val website: String, val username: String, val password: String)

fun listPasswords() {

    try {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://192.168.1.120:7878/seckey/list_all/"))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        stringToJson(response.body())

    } catch (e: Exception) {
        System.err.println("Server has crashed ($e)")
    }

}
fun stringToJson(json: String): List<Password> {
    //deserializing the json value
    val listPasswords = Json.decodeFromString<List<Password>>(json)
    return(listPasswords)

}