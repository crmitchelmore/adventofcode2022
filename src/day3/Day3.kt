package day3

import helpers.ReadFile

class Day3 {
    val lines = ReadFile.named("src/day3/input.txt")
//    val lines = listOf(
//    "vJrwpWtwJgWrhcsFMMfFFhFp",
//            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
//            "PmmdzqPrVvPwwTWBwg",
//            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
//            "ttgJtRGJQctTZtZT",
//            "CrZsJsPPZsGzwwsLwLmpwMDw"
//    )
    var all = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    fun result1(): String {
        return lines.map {
            val chars = it.split("").filter { it.isNotEmpty() }
            val half = it.count()/2
            val left = chars.subList(0, half)
            val right = chars.subList(half, it.count())
            val overlap = left.toSet().intersect(right.toSet())
            println("L: $left R: $right O: $overlap S: ${all.indexOf(overlap.first()) + 1}")
            all.indexOf(overlap.first()) + 1
        }.sum().toString()
    }

    fun result2(): String {
        return ReadFile.named("src/day3/input.txt").map { it.split("").filter { it.isNotEmpty() } }.chunked(3).map {
            it.drop(1).fold(it.first().toSet()) { a, b ->
                a.intersect(b.toSet())
            }.sumOf {
                (('a'..'z') + ('A'..'Z')).toList().map { it.toString() }.indexOf(it) + 1
            }
        }.sum().toString()
    }
}