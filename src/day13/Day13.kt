package day13

import helpers.ReadFile
import java.lang.Integer.min


class Day13 {
    val lines = ReadFile.named("src/day13/input.txt", "\n\n")
    val linet = ReadFile.named("src/day13/testinput.txt", "\n\n")

    class RecursiveArray {
        var children: MutableList<RecursiveArray>? = null
        var number: Int? = null

        constructor(number: Int? = null, children: MutableList<RecursiveArray>? = null) {
            this.number = number
            this.children = children
        }
    }
    //[[[[2]],[2,[3],[9,3],10],8],[[9,[5,7,5,5],6,8],[[],7,7,2]],[[]]]
    fun parseLine(line: String): Pair<RecursiveArray, String> {
        var remaining = line.drop(1)
        var ra = RecursiveArray(children = mutableListOf())
        while (true) {
            if (remaining.startsWith(",")){
                remaining = remaining.drop(1)
            }
            if (remaining.startsWith("[")) {
                val (el, r) = parseLine(remaining)
                ra.children?.add(el)
                remaining = r
            } else if (remaining.startsWith("]")) {
                return Pair(ra, remaining.drop(1))
            } else {
                val number = remaining.takeWhile { it.isDigit() }
                val el = RecursiveArray()
                el.number = number.toInt()
                remaining = remaining.drop(number.length)
                ra.children?.add(el)

                if (remaining.startsWith(",")){
                    remaining = remaining.drop(1)
                }
            }
        }
    }

    fun aLTb(a: RecursiveArray, b: RecursiveArray): Boolean? {
        if (a.number != null && b.number != null && a.number != b.number) {
            return a.number!! < b.number!!
        }
        if (a.children != null && b.children != null) {
            if (a.children!!.size == 0 || b.children!!.size == 0) {
                if (a.children!!.size == b.children!!.size) {
                    return null
                }
                return a.children!!.size < b.children!!.size
            }

            for (i in 0 until  min(a.children!!.size, b.children!!.size)) {
                if (a.children!![i].children != null && b.children!![i].number != null) {
                    val result = aLTb(a.children!![i], RecursiveArray(children = mutableListOf(b.children!![i])))
                    if (result != null) {
                        return result
                    }
                } else if (b.children!![i].children != null && a.children!![i].number != null) {
                    val result = aLTb(RecursiveArray(children = mutableListOf(a.children!![i])), b.children!![i])
                    if (result != null) {
                        return result
                    }
                } else {
                    val result = aLTb(a.children!![i], b.children!![i])
                    if (result != null) {
                        return result
                    }
                }
            }
            if (a.children!!.size != b.children!!.size) {
                return a.children!!.size < b.children!!.size
            }
        }

        return null
    }
    fun result1() {
        var i = 1
        var sum = 0
        for (block in lines) {
            val lines = block.split("\n")
            val left = parseLine(lines[0]).first
            val right = parseLine(lines[1]).first
            val compare = aLTb(left, right)
            if (compare == true) {
                sum += i
            }
            println("$i $compare")
            i++

        }
        print("P1 sum: $sum")

        val lines = ReadFile.named("src/day13/input.txt").filter { it.isNotBlank() }.map { parseLine(it).first }
        val linet = ReadFile.named("src/day13/testinput.txt").filter { it.isNotBlank() }.map { parseLine(it).first }

        val sorted = lines.sortedWith(Comparator { o1, o2 ->
            val compare = aLTb(o1, o2)
            if (compare == true) {
                return@Comparator -1
            }
            if (compare == false) {
                return@Comparator 1
            }
            return@Comparator 0
        })

        val a = sorted.indexOfFirst { it.children?.size == 1 && it.children!![0].children?.size == 1 && it.children!![0].children!![0].number == 2 } + 1
        val b = sorted.indexOfFirst { it.children?.size == 1 && it.children!![0].children?.size == 1 && it.children!![0].children!![0].number == 6 } + 1
        println(a*b)



    }


}