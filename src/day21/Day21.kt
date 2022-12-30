package day21

import helpers.ReadFile
import java.math.BigDecimal

class Day21 {

    val tlines = listOf(
        "root: pppw + sjmn",
                "dbpl: 5",
                "cczh: sllz + lgvd",
                "zczc: 2",
                "ptdq: humn - dvpt",
                "dvpt: 3",
                "lfqf: 4",
                "humn: 5",
                "ljgn: 2",
                "sjmn: drzm * dbpl",
                "sllz: 4",
    "pppw: cczh / lfqf",
    "lgvd: ljgn * ptdq",
    "drzm: hmdt - zczc",
    "hmdt: 32"
    )

    val rlines = ReadFile.named("src/day21/input.txt")

    class Calc {
        val name: String
        var num: BigDecimal? = null
        var op: String? = null
        var refA: String? = null
        var refB: String? = null
        constructor(name: String) {
            this.name = name
        }
    }
    val lines = rlines
    var nums = mutableMapOf<String, Calc>()

    fun aopb(a: BigDecimal, b: BigDecimal, op: String): BigDecimal {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw Exception("Unknown op $op")
        }
    }

    fun recurse(c: Calc): BigDecimal {
        if (c.num != null) {
            return c.num!!
        }

        val lhs = if (nums[c.refA]?.num != null) nums[c.refA]!!.num!! else recurse(nums[c.refA]!!)
        val rhs = if (nums[c.refB]?.num != null) nums[c.refB]!!.num!! else recurse(nums[c.refB]!!)
        return aopb(lhs, rhs, c.op!!)

    }

    fun isLower(i: Long) : Boolean {
        val a = nums[nums["root"]!!.refA]!!
        val b = "13751780524553".toBigDecimal()
        val human = nums["humn"]!!


        human.num = BigDecimal(i)
        val result = recurse(a)
        if (i % 1000 == 0L) {
            println("$i $result")
        }
        if (result == b) {
            println("Win : $i")
            throw Exception("Win")
        }
        return result < b
    }
    fun result1() {
        lines.forEach { line ->
            val parts = line.split(": ")
            var calc = Calc(parts[0])
            calc.num = parts[1].toBigDecimalOrNull()
            if (calc.num == null) {
                val refs = parts[1].split(" ")
                calc.op = refs[1]
                calc.refA = refs[0]
                calc.refB = refs[2]

            }
            nums[calc.name] = calc
        }


        val a = nums[nums["root"]!!.refA]!!
        val b = "13751780524553".toBigDecimal()
                 24440927425391
                 28105437225723
                 28068793716397
                 24440928000721
                 //nums[nums["root"]!!.refB]!!
        val human = nums["humn"]!!
        println(recurse(a))

        var range = 1000000000000L..10000000000000L
        while (true) {
            val mid = (range.first + range.last) / 2
            if (isLower(mid)) {
                println("lower")
                range = range.first..mid

            } else {
                println("higher")
                range = mid..range.last
            }
        }


    }


}