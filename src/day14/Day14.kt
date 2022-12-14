package day14

import helpers.ReadFile

class Day14 {
    val lines = ReadFile.named("src/day14/input.txt")

    val linet = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9",
    )
    fun printBoard(board: List<List<String>>) {
        for (row in board) {
            println(row.joinToString(""))
        }
    }
    fun result1() {
        val plots = lines.map { it.split(" -> ").map {
                val bits = it.split(",")
                Pair(bits[0].toInt(), bits[1].toInt())
            }
        }

        var maxX = 0
        var maxY = 0
        plots.flatten().forEach() {
            maxX = maxOf(maxX, it.first)
            maxY = maxOf(maxY, it.second)
        }
        maxY += 3
        val sandPoint = Pair(500, 0)
        val normalisedLines = plots.map { it.map { Pair(it.first, it.second) } }
        var board = mutableListOf<MutableList<String>>()
        for (y in 0 until maxY) {
            board.add(mutableListOf())
            for (x in 0..maxX*2) {
                board[y].add(".")
            }
        }

        normalisedLines.forEach { line ->
            for (i in 0 until line.size - 1) {
                val a = line[i]
                val b = line[i + 1]
                if (a.first == b.first) {
                    for (y in minOf(a.second, b.second)..maxOf(a.second, b.second)) {
                        board[y][a.first] = "#"
                    }
                } else {
                    for (x in minOf(a.first, b.first)..maxOf(a.first, b.first)) {
                        board[a.second][x] = "#"
                    }
                }
            }
        }
        var bottom = mutableListOf<String>()
        for (x in 0..maxX*2) {
            bottom.add("#")
        }
        board[board.size-1] = bottom
        var units = 0
        while (true) {
            var y = sandPoint.second
            var x = sandPoint.first

            var moving = true
            while (moving) {
                if (y >= maxY) {
                    println("Done: units: $units")
                    return
                } else if (board[y+1][x] == ".") {
                  y++;
                } else if (board[y+1][x-1] == ".") {
                    x--;
                    y++
                } else if (board[y+1][x+1] == ".") {
                    x++;
                    y++
                } else {
                    moving = false
                    board[y][x] = "o"
                    units++
                    if (y == sandPoint.second && x == sandPoint.first) {
                        printBoard(board)
                        println("DD Done: units: $units")
                        return
                    }
                }
            }

        }
    }
}