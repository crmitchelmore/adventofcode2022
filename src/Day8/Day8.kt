package Day8

import helpers.ReadFile

class Tree {
    val size: Int
    var visible: Boolean
    var topVisibleSize: Int?
    var bottomVisibleSize: Int?
    var leftVisibleSize: Int?
    var rightVisibleSize: Int?
    var points = 0

    fun visibleSize(): Int {
        return if (visible) 1 else 0;
    }

    constructor(size: Int, visible: Boolean, topVisibleSize: Int? = null, bottomVisibleSize: Int? = null, leftVisibleSize: Int? = null, rightVisibleSize: Int? = null) {
        this.size = size
        this.visible = visible
        this.topVisibleSize = topVisibleSize
        this.bottomVisibleSize = bottomVisibleSize
        this.leftVisibleSize = leftVisibleSize
        this.rightVisibleSize = rightVisibleSize
    }
}
class Day8 {
    val digits = ReadFile.named("src/Day8/input.txt").map { it.split("").filter { it.isNotEmpty() }.map { Tree(size = it.toInt(), visible = false)  } }
//    val digits = listOf(
//        "30373",
//        "25512",
//        "65332",
//        "33549",
//        "35390"
//    ).map { it.split("").filter { it.isNotEmpty() }.map { Tree(size = it.toInt(), visible = false)  } }

    fun result1() : String {
        digits.first().forEach() { it.visible = true }
        digits.last().forEach() { it.visible = true }
        digits.forEach() {
            it.first().visible = true
            it.last().visible = true
        }

        var leftVisibleSize = digits.drop(1).dropLast(1).map { it.first().size }.toMutableList()
        var rigjtVisibleSize = digits.drop(1).dropLast(1).map { it.last().size }.toMutableList()
        var topVisibleSize = digits.first().drop(1).dropLast(1).map { it.size }.toMutableList()
        var bottomVisibleSize = digits.last().drop(1).dropLast(1).map { it.size }.toMutableList()


        for (i in 1 until digits.size - 1) {
            for (j in 1 until digits[i].size - 1) {

                var up = 0
                for (u in i-1 downTo 0) {
                    up += 1
                    if (digits[u][j].size >= digits[i][j].size) {
                        break
                    }
                }
                var down = 0
                for (d in i+1 until digits.size) {
                    down += 1
                    if (digits[d][j].size >= digits[i][j].size) {
                        break
                    }
                }
                var left = 0
                for (l in j-1 downTo 0) {
                    left += 1
                    if (digits[i][l].size >= digits[i][j].size) {
                        break
                    }
                }
                var right = 0
                for (r in j+1 until digits[0].size) {
                    right += 1
                    if (digits[i][r].size >= digits[i][j].size) {
                        break
                    }
                }
                digits[i][j].points = left * right * up * down

                val lv = digits[i][j].size > leftVisibleSize[i-1]
                val tv = digits[i][j].size > topVisibleSize[j-1]
                if (lv) {
                    leftVisibleSize[i-1] = digits[i][j].size
                }
                if (tv) {
                    topVisibleSize[j-1] = digits[i][j].size
                }
                if (lv || tv) {
                    digits[i][j].visible = true
                }
            }
        }

//        30373
//        25512
//        65332
//        33549
//        35390
        for (i in digits.size - 2 downTo  1) {
            for (j in digits[i].size - 2 downTo  1) {
                val rv = digits[i][j].size > rigjtVisibleSize[i-1]
                val bv = digits[i][j].size > bottomVisibleSize[j-1]
                if (rv) {
                    rigjtVisibleSize[i-1] = digits[i][j].size
                }
                if (bv) {
                    bottomVisibleSize[j-1] = digits[i][j].size
                }
                if ((rv || bv) && !digits[i][j].visible) {
                    digits[i][j].visible = true
                }
            }
        }
        println("Result")
        for (i in 0 until digits.size) {
            for (j in 0 until digits[i].size) {
                print(digits[i][j].visibleSize())
            }
            println()
            }

        val maxLos = digits.maxOf { (it.maxOf { it.points })  }
        val visible = digits.sumOf { (it.sumOf { it.visibleSize() }) }
        return maxLos.toString()

    }
}