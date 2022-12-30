package day20

import helpers.ReadFile

class Day20 {
    val tlines = listOf(
        "1",
        "2",
                "-3",
        "3",
                "-2",
        "0",
        "4",
    )

    class Number {
        val value: Long
        constructor(value: Long) {
            this.value = value
        }
    }
    val rlines = ReadFile.named("src/day20/input.txt")
    var id = 0
    val lines = rlines.map { Number(it.toLong() * 811589153) }

    fun result1() {
        var result = lines.toMutableList()
        val size = (result.size - 1).toLong()
        for (x in 0 until 10) {
            for (n in lines) {
                val i = result.indexOf(n)
                result.remove(n)
                val positivePosition = (size * 811589153 * 5 + i + n.value)
                if (positivePosition == size) {
                    result.add(n)
                } else {
                    result.add((positivePosition % size).toInt(), n)
                }
            }
        }
        val i0 = result.indexOfFirst { it.value == 0L }
        val output = (1..3).fold(0L) { acc, i -> acc + result[(i0 + i * 1000) % result.size].value }
        println(output)
    }
}