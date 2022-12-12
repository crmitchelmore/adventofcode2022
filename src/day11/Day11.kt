package day11

import helpers.ReadFile

class Day11 {
    val lines = ReadFile.named("src/day11/input.txt", blocks = "\n\n").map { it.split("\n") }
    val lines1 = listOf(
        listOf("Monkey 0:",
        "Starting items: 79, 98",
    "Operation: new = old * 19",
    "Test: divisible by 23",
    "If true: throw to monkey 2",
    "If false: throw to monkey 3",
        ),listOf(
    "Monkey 1:",
    "Starting items: 54, 65, 75, 74",
    "Operation: new = old + 6",
    "Test: divisible by 19",
    "If true: throw to monkey 2",
    "If false: throw to monkey 0",
        ),listOf(
    "Monkey 2:",
    "Starting items: 79, 60, 97",
    "Operation: new = old * old",
    "Test: divisible by 13",
    "If true: throw to monkey 1",
    "If false: throw to monkey 3",
        ),listOf(
    "Monkey 3:",
    "Starting items: 74",
    "Operation: new = old + 3",
    "Test: divisible by 17",
    "If true: throw to monkey 0",
    "If false: throw to monkey 1"
    ))

    class Monkey {
        var times = 0
        val name: Int
        var items: MutableList<Long>
        var operation: (Long) -> Long
        val divideBy: Long
       var test: (Long) -> Int

       constructor(input: List<String>) {
            name = input[0].split(" ")[1].dropLast(1).toInt()
            items = input[1].drop(16).split(", ").map { it.toLong() }.toMutableList()
           val op = input[2].drop(21).split(" ")
           divideBy = input[3].drop(19).toLong()
           val worryLevelFactory: Long = 1
           val tr = input[4].split(" ").last().toInt()
           val fal = input[5].split(" ").last().toInt()
            operation =  when (op[0]) {
                "+" -> { x -> (x + (op[1].toLongOrNull()?:x))/worryLevelFactory }
                else -> { x -> (x * (op[1].toLongOrNull()?:x))/worryLevelFactory } // Times
            }
           val zero: Long = 0
           test = { x -> if (x % divideBy == zero) tr else fal }
       }

    }
    fun result1() : String {
        val monkeys = lines.map { Monkey(it) }
        val factor = monkeys.fold(1L) { acc, monkey -> acc * (monkey.divideBy) }
        val rounds = 10000

        for (i in 1..rounds) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val newItem = monkey.operation(item)
                    val target = monkey.test(newItem)
                    monkeys[target].items.add(newItem % factor)
                    monkey.times++
                }
                monkey.items = mutableListOf()
            }
            if (i == 1 || i == 20 || i == 1000 || i == 10000) {
                println(monkeys.map { it.times })
            }
        }

        val m = monkeys.sortedBy { it.times }.takeLast(2).map { it.times }

        return (m[0] * m[1]).toString()
    }
}