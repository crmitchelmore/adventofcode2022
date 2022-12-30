package day24

import helpers.ReadFile

class Day24 {
    val tlines = listOf(
        "#.######",
        "#>>.<^<#",
        "#.<..<<#",
        "#>v.><>#",
        "#<^v^^>#",
        "######.#",
    )
    val rlines = ReadFile.named("src/day24/input.txt")


    val lines = rlines.drop(1).dropLast(1).map { it.split("").filter { it.isNotEmpty() }.drop(1).dropLast(1) }

    val boardHeight = lines.size
    val boardWidth = lines.first().size
    enum class BlizzardDir {
        UP, DOWN, LEFT, RIGHT
    }

    class Blizzard {
        val dir: BlizzardDir
        var pos: Pair<Int, Int>
        fun toStr(): String {
            return when (dir) {
                BlizzardDir.UP -> "^"
                BlizzardDir.DOWN -> "v"
                BlizzardDir.LEFT -> "<"
                BlizzardDir.RIGHT -> ">"
            }
        }
        fun move(h: Int, w: Int) : Blizzard {
            if (dir == BlizzardDir.UP) {
                return Blizzard(dir, Pair(pos.first, (h + pos.second-1) % h))
            } else if (dir == BlizzardDir.DOWN) {
                return Blizzard(dir, Pair(pos.first, (h + pos.second+1) % h))
            } else if (dir == BlizzardDir.LEFT) {
                return Blizzard(dir, Pair((w + pos.first -1) % w, pos.second))
            } else {
                return Blizzard(dir, Pair((w + pos.first+1) %w, pos.second))
            }
        }
        constructor(dir: BlizzardDir, pos: Pair<Int, Int>) {
            this.dir = dir
            this.pos = pos
        }
    }

        fun drawBliz(bliz: List<Blizzard>) {
        for (y in 0 until boardHeight) {
            for (x in 0 until boardWidth) {
                val c = bliz.filter { it.pos == Pair(x,y) }
                if (c.isEmpty()) print(".") else if (c.count() == 1) print(c.first().toStr()) else print(c.count())
            }
            println("")
        }
    }  fun breath(t: Int, currentPositions: Set<Pair<Int, Int>>, bz: List<Blizzard>, goal: Pair<Int, Int>):  Pair<Int, List<Blizzard>>  {

        var nextBliz = bz
        var todopos = currentPositions
        var turn = t
        while (true) {
            turn += 1
            var blizPositionSet = mutableSetOf<Pair<Int, Int>>()
            nextBliz = nextBliz.map {
                val next = it.move(boardHeight, boardWidth)
                blizPositionSet.add(next.pos)
                next
            }
            var nextPositions = mutableSetOf<Pair<Int, Int>>()
            var found = false
            if (turn == 5) {
                println("here")
            }
            for (currentPos in todopos) {
                var options = mutableSetOf(currentPos)
                if (currentPos.second in 0 until boardHeight) {
                    if (currentPos.first + 1 < boardWidth) {
                        options.add(Pair(currentPos.first + 1, currentPos.second))
                    }
                    if (currentPos.first - 1 >= 0) {
                        options.add(Pair(currentPos.first - 1, currentPos.second))
                    }
                }

                if (currentPos.second < boardHeight - 1 || (currentPos.second == boardHeight - 1 && currentPos.first == boardWidth-1)) {
                    options.add(Pair(currentPos.first, currentPos.second + 1))
                }
                if (currentPos.second - 1 >= 0 || (currentPos.second == 0 && currentPos.first == 0)) {
                    options.add(Pair(currentPos.first, currentPos.second -1))
                }

                nextPositions.addAll(options)
            }
            nextPositions.removeAll(blizPositionSet)
//            println("turn: $turn nextPositions: $nextPositions")
//            drawBliz(nextBliz)
            if (nextPositions.contains(goal)) {
                found = true
            }

            if (turn % 100 == 0) {
                print("Turn $turn: ")
            }
            if (found) {
                return Pair(turn, nextBliz)
            }

            todopos = nextPositions
        }



    }


    fun result1() {
        var blizzards = mutableListOf<Blizzard>()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] == "<") {
                    blizzards.add(Blizzard(BlizzardDir.LEFT, Pair(j, i)))
                } else if (lines[i][j] == ">") {
                    blizzards.add(Blizzard(BlizzardDir.RIGHT, Pair(j, i)))
                } else if (lines[i][j] == "^") {
                    blizzards.add(Blizzard(BlizzardDir.UP, Pair(j, i)))
                } else if (lines[i][j] == "v") {
                    blizzards.add(Blizzard(BlizzardDir.DOWN, Pair(j, i)))
                }
            }
        }

        val botcorn = Pair(boardWidth - 1, boardHeight)
        val topcorn = Pair(0, -1)
        val res = breath(0, setOf(topcorn), blizzards, botcorn)
        println("Win ${res.first}")
        val res2 = breath(0, setOf(botcorn), res.second, topcorn)
        println("Win2 ${res2.first}")
        val res3 = breath(0, setOf(topcorn), res2.second, botcorn)
        println("Win3 ${res3.first}")
        println("Total: ${res.first + res2.first + res3.first}")

    }
}

