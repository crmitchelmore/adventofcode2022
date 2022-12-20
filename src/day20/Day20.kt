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
        val id: Int
        val value: Long
        override fun toString(): String {
            return "$value"
        }
        constructor(value: Long, id: Int) {
            this.value = value
            this.id = id
        }
    }
    val rlines = ReadFile.named("src/day20/input.txt")
    var id = 0
    val lines = rlines.map { Number(it.toLong() * 811589153, id++) }

    fun result1() {
        var result = lines.toMutableList()
        val size = (result.size - 1).toLong()
        for (x in 0 until 10) {
            for (n in lines) {
                val i = result.indexOf(n)
                result.remove(n)
                val pos = (size * 811589153 * 5 + i + n.value)
                if (pos == size) {
                    result.add(n)
                } else {
                    result.add((pos % size).toInt(), n)
                }
            }
        }
        val i0 = result.indexOfFirst { it.value == 0L }
        val a = result[(1000 + i0) % result.size].value
        val b = result[(2000 + i0) % result.size].value
        val c = result[(3000 + i0) % result.size].value
        println(a+b+c)
    }
}