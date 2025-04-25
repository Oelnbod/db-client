import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val client = HttpClient.newBuilder().build()



fun main() {
    listPasswords()


}

fun listPasswords() {
    try {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://192.168.1.120:7878/seckey/list_all/"))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())

    } catch (e: Exception) {
        System.err.println("Server has crashed ($e)")
    }

}