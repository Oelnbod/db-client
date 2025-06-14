package api
//this package is an abstraction layer to handle http requests

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

// this will list convert a json string into a table of passwords
fun stringToJson(json: String): List<Password> {
    //deserializing the json value
    val listPasswords = Json.decodeFromString<List<Password>>(json)
    return (listPasswords)

}

// this queries the api with the required uri, with error handling
fun queryAPI(url: String): String {
    val client = HttpClient.newBuilder().build()
    try {
        val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return (response.body())
    } catch (e: Exception) {
        System.err.println(
            "" + "------------------------------------------------------------------------\n" + "                        :(\n" + " Believed Error: Server has crashed ($e)\n Please reboot the password server\n Error details  printed below: \n " +

                    "------------------------------------------------------------------------"
        )
        return ("")
    }

}

@Serializable
data class Password(val id: UInt, val website: String, val username: String, val password: String)