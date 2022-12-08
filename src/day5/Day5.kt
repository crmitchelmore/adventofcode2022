package day5

import helpers.ReadFile
//"                    [Q]     [P] [P]"
class Day5 {
    val lines = ReadFile.named("src/day5/input.txt")
    fun result1(): String {
        val moves = lines.filter { it.startsWith("move") }.map {
            it.split(" ").filter { it.toCharArray().first().isDigit() }.map { it.toInt() - 1 }
        }

        val board = lines.filter { !it.startsWith("move") }
        val rows: List<List<Char>> = board.dropLast(2).map {line ->
            (0..8).map {
                ("$line ".substring(4*it, 4*it + 4).filter { it.isLetter() }.firstOrNull() ?: ' ')
            }
        }
        val cols = MutableList<MutableList<Char>>(size = 9, init = { MutableList<Char>(size = 0, init = { ' ' }) })
        rows.reversed().forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                if (col != ' ') {
                    cols[colIndex].add(col)
                }
            }
        }
        println(cols)
        for (move in moves) {
            val bits = cols[move[1]].takeLast(move[0] + 1)
            cols[move[1]] = cols[move[1]].dropLast(move[0] + 1).toMutableList()
            cols[move[2]].addAll(bits)
        }

        return cols.map { it.last() }.joinToString("") { it.toString() }
    }

}