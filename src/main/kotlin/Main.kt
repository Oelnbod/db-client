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
        val json = response.body() //this is the json return from the api query
        //decoding the json and printing it
        // val listPasswords = Json.decodeFromString<List<Password>>(json)
        println(listPasswords)
    } catch (e: Exception) {
        System.err.println("Server has crashed ($e)")
    }

}