package day4;

import helpers.ReadFile

class Day4 {
    val lines = ReadFile.named("src/day4/input.txt")
    fun result1(): String {
        return lines.filter {
            val split = it.split(",")
            val rangePair = split.map {
                val r = it.split("-")
                (r[0].toInt().. r[1].toInt())
            }
            val intersection = rangePair[0].intersect(rangePair[1])
            intersection.isNotEmpty()
        }.count().toString()

    }
}
