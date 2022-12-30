package day25

import helpers.ReadFile
import java.math.BigInteger

class Day25 {

    fun parseSnafu(string: String) : Long {
        val chars = string.split("").filter { it.isNotEmpty() }.reversed()
        var mult = 1L
        var total = 0L
        for (char in chars) {
            if (char.toCharArray().first().isDigit()) {
                total += char.toLong() * mult
            } else if (char == "-") {
                total -= mult
            } else {
                total -= 2 * mult
            }
            mult *= 5
        }
        return total
    }

    fun toSnafu(long: Long) : String {

        println("Long: $long Base 5: ${BigInteger.valueOf(long).toString(5)} Back: ${BigInteger.valueOf(long).toString(5).toBigInteger(5).toString(10)}")
        var base5 = BigInteger.valueOf(long).toString(5).split("").filter { it.isNotEmpty() }.map{ it.toInt() }.reversed().toMutableList()
        var result = ""
        //12322024001244014000
        //2==221=-002=0-02-000
        for (i in base5.indices) {
            if (base5[i] > 4) {
                base5[i] -= 5
                if (i+1 == base5.size) {
                    base5.add(1)
                } else {
                    base5[i + 1] += 1
                }
            }
            if (base5[i] < 3) {
               result = base5[i].toString() + result
            } else {
                if (i+1 == base5.size) {
                    base5.add(0)
                }
                base5[i+1] += 1
                result = (if (base5[i] == 4) "-" else "=") + result
            }
        }
        return result
    }
    val lines = ReadFile.named("src/day25/input.txt")
    fun result1() {
        val nums = lines.map { BigInteger.valueOf(parseSnafu(it)) }
        val total = nums.fold(BigInteger.valueOf(0L)) { a, b ->
            a + b
        }

            //29361331235500
            //33939944735375
        println(parseSnafu("2--221-=002-0=02=000"))
//        println(toSnafu(total.toLong()))

    }
    //2--221-=002-==02=000.
    //2--221-=002-0=02=000
    //2==221=-002=0-02-000
}