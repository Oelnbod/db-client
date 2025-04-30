import api.addPasswords
import api.deletePasswords
import api.listAllPasswords
import api.listSomePasswords

fun main() {
    println(addPasswords("p.p", "uid", "pwd"))
    println(listAllPasswords())
    println(listSomePasswords("example.com"))
    println(deletePasswords("p.p"))
    println(listAllPasswords())
}

