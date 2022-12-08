package day1

import helpers.ReadFile

class Day1 {
    internal inner class State {
        var total = 0
        var max = 0

        fun addToTotal(amount: Int) {
            total += amount
            max = Math.max(total, max)
        }

        fun reset() {
            total = 0
        }
    }

    internal inner class State2 {
        var total = 0
        var max = listOf<Int>(0,0,0)

        fun addToTotal(amount: Int) {
            total += amount

        }

        fun reset() {
            var insert = Math.max(total, max.first())
            max = (max.drop(1) + insert).sorted()
            total = 0
        }
    }

    fun result(): String {
        val lines = ReadFile.named("src/day1/input.txt")
        var s = lines.fold(State()) { a: State, b: String ->
            if (b.isNotEmpty()) {
                a.addToTotal(b.toInt())
            } else {
                a.reset()
            }
            a
        }
        return s.max.toString()
    }

    fun result2(): String {
        val lines = ReadFile.named("src/day1/input.txt")
        var s = lines.fold(State2()) { a: State2, b: String ->
            if (b.isNotEmpty()) {
                a.addToTotal(b.toInt())
            } else {
                a.reset()
            }
            a
        }
        return s.max.sum().toString()
    }
}