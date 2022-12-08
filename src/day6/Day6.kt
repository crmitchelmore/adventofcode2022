package day6

import helpers.ReadFile

class Day6 {
    val lines = ReadFile.named("src/day6/input.txt")

    fun result1(): Int {
        val chars = lines.first().split("").filter { it.isNotEmpty() }
        var last4 = chars.take(14).toMutableList()

        var next = 4
        println("first $last4 (${last4.toSet().size})")

        while (last4.toSet().size < 14) {

            last4 = last4.drop(1).toMutableList()
            println("dropped $last4")
            last4.add(chars[next])
            next++
            println(last4.toSet())
        }
        return next
    }
}