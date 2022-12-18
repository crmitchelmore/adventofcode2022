package day17

import helpers.ReadFile

class Day17 {

    val lines = ReadFile.named("src/day17/input.txt")
//    val lines = listOf(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
    fun collision(window: List<String>, block: List<String>, x: Int): Boolean {
        for (i in 0 until block.count()) {
            val line = window[i]
            val blockLine = block[i]
            for (j in x until x + blockLine.length) {
                if (blockLine[j-x] == '#' && line[j] == '#') {
                    return true
                }
            }
        }
        return false
    }

    fun paddedBoard(board: MutableList<String>, candidateY: Int, blockHeight: Int): List<String> {
        while (board.size < candidateY + blockHeight) {
            board.add(".".repeat(7))
        }
        return board.subList(candidateY, candidateY + blockHeight)
    }

    fun addBlockToBoard(board: MutableList<String>, block: List<String>, blockPos: Pair<Int, Int>): Int {
        //Pad block
        val paddedBlock = block.map {
            (".".repeat(blockPos.first) + it + ".".repeat(7)).take(7)
        }

        for (i in blockPos.second until blockPos.second + block.count()) {
            if (i >= board.count()) {
                board.add(paddedBlock[i])
            } else {
                val line = board[i]
                val blockLine = paddedBlock[i-blockPos.second]
                val newLine = (0 until 7).map {
                    if (line[it] == '#' || blockLine[it] == '#') {
                        "#"
                    } else {
                        "."
                    }
                }.joinToString("")
                board[i] = newLine
            }

        }
        //Update highest rock
        for (i in board.size - 1 downTo 0) {
            if (board[i].contains('#')) {
                return i + 1
            }
        }
        return 0
    }
    var uniques: MutableSet<Pair<Int, String>> = mutableSetOf()
    fun highestRock(moves: List<Int>, goal: Int): Int {
        var rockCount = 0
        var tick = 0
        val blocks = listOf(
            listOf("####"),
            listOf(
                ".#.",
                "###",
                ".#."),
            listOf(
                "###",
                "..#",
                "..#",),
            listOf(
                "#",
                "#",
                "#",
                "#",),
            listOf(
                "##",
                "##",),
        )
        var highestRock = 0
        var board = mutableListOf<String>(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
        )
        var markers = mutableListOf<Int>()
        //>>>< <><>><<<>><>>><<<>>><<<><<<>><>><<>>
        while (rockCount < goal) {
//            if (rockCount % moves.size == 0) {
//                markers.add(highestRock)
//                if (markers.size == 10005) {
//                    println(markers)
//                    for (gap in 1..5000) {
//                        val differences = (gap until markers.size).map {
//                            markers[it] - markers[it - gap]
//                        }.toSet()
//                        if (differences.size < 10) {
//                            println("Gap $gap : $differences")
//                        }
//                    }
//                    val u = 9
//                }
//
//            }
//            if (board.size > 150) {
//                val p = Pair(
//                    (tick % moves.size) * 10 + (rockCount % 5),
//                    board.dropLastWhile { it == "......." }.takeLast(66).joinToString("")
//                )
//                if (uniques.contains(p)) {
////                    print(board.dropLastWhile { it == "......." }.takeLast(50).joinToString("\n"))
////                    println(rockCount)
//                } else {
//                    uniques.add(p)
//                }
//                if (rockCount % 10000 == 0) {
//                    println("Size: ${uniques.size}")
//                }
//            }
            // Add block at right height
            var blockPos = Pair(2, highestRock + 3)
            val block = blocks[rockCount % 5]
            val blockWidth = block.first().length
            val blockHeight = block.count()

            // Move
            while (true) {
                val move = moves[tick % moves.size]
                // LR Movement
                if (blockPos.first + move >= 0 && blockPos.first + move + blockWidth <= 7 && !collision(paddedBoard(board, blockPos.second, blockHeight), block, blockPos.first + move)) {
                    blockPos = Pair(blockPos.first + move, blockPos.second)
                }
                tick++
                // Down movement
                val candidateY = blockPos.second - 1
                if (candidateY < 0 || collision(paddedBoard(board, candidateY, blockHeight), block, blockPos.first)) {
                    // Add block to board
                    highestRock = addBlockToBoard(board, block, blockPos)
                    break
                } else {
                    blockPos = Pair(blockPos.first, candidateY)
                }


            }
//            println(board.reversed().joinToString("\n"))
            rockCount++
        }
        return highestRock
    }
    fun result1() {
        //1514285714288
        //1514285714288
        //1514285714288
        //1514285714286
        val moves = lines.first().split("").filter { it.isNotEmpty() }.map { if (it == ">") 1 else -1 }
        //magic factor 371

        val factor = 1730 * moves.size
        val goal = 1000000000000
        val remainder = (goal % factor)
        val nubbin = highestRock(moves, moves.size)
        val repeatable = highestRock(moves, factor + moves.size) - nubbin

        println(repeatable * (goal/factor) + highestRock(moves, remainder.toInt()))
    }
    //Guessed 1536992612548: too low
    //////////1536992612529
}