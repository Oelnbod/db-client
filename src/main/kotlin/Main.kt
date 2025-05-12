import api.Password
import api.listAllPasswords

fun main() {
    //parsing it to independent lists by column appending at the start is to give titles to column
    val data = listAllPasswords()
    displayAsTable(data)



// other commands available
//   println(addPasswords("p.p", "uid", "pwd"))
//   println(listAllPasswords())
//   println(listSomePasswords("example.com"))
//   println(deletePasswords("p.p"))
//    println(listAllPasswords())
}

fun largest(list: List<String>): Int {
    //this works by adding to a list and then sorting, reversing and taking the first value
    val lengthsList = mutableListOf<Int>()
    for (items in list) {
        lengthsList.add(items.length)

    }
    return (lengthsList.sorted().reversed()[0])

}

fun padding(input: String, target: Int): String {
    //This is for a consistent length of column
    var input = input
    var suitableLength = false
    //println("TARGET:  $target")
    while (!suitableLength) {
        if (input.length == target) {
            suitableLength = true
        } else {
            input = "$input "
            //println(input)
        }
    }
    return (input)
}

fun displayAsTable(data: List<Password>) {
    val idList: List<String> = listOf("Index:") + (data.map { it.id.toString() })
    val websiteList: List<String> = listOf("Website:") + data.map { it.website }
    val usernameList: List<String> = listOf("Usernames:") + data.map { it.username }
    val passwordList: List<String> = listOf("Passwords:") + data.map { it.password }

    //merging lists together through .zip
    val fullList = idList.zip(websiteList.zip(usernameList.zip(passwordList)))
    //converting to an array for convenience
    val fullArray: Array<Array<String>> = fullList.map {
        arrayOf(it.first, it.second.first, it.second.second.first, it.second.second.second)
    }.toTypedArray()

    //identifying length so can be referenced by index
    val lengthAll: List<Int> =
        listOf(largest(idList), largest(websiteList), largest(usernameList), largest(passwordList))
    for (row in fullArray) {
        //println()

        for (item in row) {
            //getting a limit for padding from list above
            val paddingLimit: Int = lengthAll[row.indexOf(item)]
            //padding using limit above
            val stringItem = padding(item, paddingLimit)

            //inserting a pipe beforehand
            print("| $stringItem  ")


        }
        //adding a pipe to the end of each row and a blank line
        print("|")
        println()
    }

}