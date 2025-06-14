package stringHandling

import java.io.BufferedReader
import java.io.File

fun readFromFile(file: String): String {
    //this is for reading the key
    val bufferedReader: BufferedReader = File("src/main/resources/$file").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return (inputString)
}

fun writeToFile(file: String, text: String) {
    File("src/main/resources/$file").writeText(text)
}