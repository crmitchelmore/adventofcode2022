package helpers

import java.io.File
import java.io.FileNotFoundException
import java.util.*

// Import the File class
// Import this class to handle errors
// Import the Scanner class to read text files
object ReadFile {
    fun named(name: String?): List<String> {
        val text = File(name).readText()

        return text.split("\n")
    }
}