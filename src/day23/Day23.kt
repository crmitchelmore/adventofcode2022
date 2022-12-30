package day23

import helpers.ReadFile

class Day23 {
    val rlines = ReadFile.named("src/day23/input.txt")
    val tlines = listOf(
"....#..",
"..###.#",
"#...#.#",
".#...##",
"#.###..",
"##.#.##",
".#..#.."
    )
    val lines = rlines

    class Elf(line: Int, row: Int) {
        var pos: Pair<Int, Int>

        init {
            this.pos = Pair(line, row)
        }
    }
    fun result1() {
//        val x = listOf(
//            ".....#...",
//        "...#...#.",
//        ".#..#.#..",
//        ".....#..#",
//        "..#.#.##.",
//        "#..#.#...",
//        "#.#.#.##.",
//        ".........",
//        "..#..#...",
//        ).map { it.split("").filter { it != "" } }
//        for (line in x.indices) {
//            for (char in x[line].indices) {
//                if (x[line][char] == "#") {
//                    println("${line-1},${char-1}")
//                }
//            }
//        }
//        return

        var allPositions = mutableMapOf<Int, MutableMap<Int, Elf>>()
        val allElves = mutableListOf<Elf>()
        for (line in lines.indices) {
            val aLine = lines[line].split("").filter { it.isNotEmpty() }
            for (char in aLine.indices) {
                if (aLine[char] == "#") {
                    val elf = Elf(line, char)
                    allElves.add(elf)
                    allPositions.putIfAbsent(line, mutableMapOf())
                    allPositions[line]!![char] = elf
                }
            }
        }

        var direction = 0


        var times = 0
        while (true) {
            val proposedMoves = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Boolean, Elf>>>()
            var moved = false
            for (elf in allElves) {
                val north = allPositions[elf.pos.first - 1]?.get(elf.pos.second) == null
                val south = allPositions[elf.pos.first + 1]?.get(elf.pos.second) == null
                val west = allPositions[elf.pos.first]?.get(elf.pos.second - 1) == null
                val east = allPositions[elf.pos.first]?.get(elf.pos.second + 1) == null
                val southEast = allPositions[elf.pos.first + 1]?.get(elf.pos.second + 1) == null
                val southWest = allPositions[elf.pos.first + 1]?.get(elf.pos.second - 1) == null
                val northEast = allPositions[elf.pos.first - 1]?.get(elf.pos.second + 1) == null
                val northWest = allPositions[elf.pos.first - 1]?.get(elf.pos.second - 1) == null
                if (north && south && west && east && southEast && southWest && northEast && northWest) {
                    val proposed = Pair(elf.pos.first, elf.pos.second)
                    proposedMoves.putIfAbsent(proposed, mutableListOf())
                    proposedMoves[proposed]!!.add(Pair(false, elf))
                    continue
                }
                var foundElfMove = false
                for (i in 0 until 4) {
                    val j = (i + direction) % 4
                    if (j == 0) {
                        if (north && northEast && northWest) {
                            val proposed = Pair(elf.pos.first - 1, elf.pos.second)
                            proposedMoves.putIfAbsent(proposed, mutableListOf())
                            proposedMoves[proposed]!!.add(Pair(true, elf))
                            moved = true
                            foundElfMove = true
                            break
                        }
                    }
                    if (j == 1) {
                        if (south && southEast && southWest) {
                            val proposed = Pair(elf.pos.first + 1, elf.pos.second)
                            proposedMoves.putIfAbsent(proposed, mutableListOf())
                            proposedMoves[proposed]!!.add(Pair(true, elf))
                            moved = true
                            foundElfMove = true
                            break
                        }

                    }
                    if (j == 2) {
                        if (west && northWest && southWest) {
                            val proposed = Pair(elf.pos.first, elf.pos.second - 1)
                            proposedMoves.putIfAbsent(proposed, mutableListOf())
                            proposedMoves[proposed]!!.add(Pair(true, elf))
                            moved = true
                            foundElfMove = true
                            break
                        }
                    }

                    if (j == 3) {
                        if (east && northEast && southEast) {
                            val proposed = Pair(elf.pos.first, elf.pos.second + 1)
                            proposedMoves.putIfAbsent(proposed, mutableListOf())
                            proposedMoves[proposed]!!.add(Pair(true, elf))
                            moved = true
                            foundElfMove = true
                            break
                        }
                    }
                }
                if (!foundElfMove) {
                    val proposed = Pair(elf.pos.first, elf.pos.second)
                    proposedMoves.putIfAbsent(proposed, mutableListOf())
                    proposedMoves[proposed]!!.add(Pair(false, elf))
                }
            }
//            println("Moves: $times")
//            allElves.forEach {
//                println(it.pos)
//            }

            if (!moved /*|| times == 10*/) {
                println("No moves round: ${times + 1}")
                var min = Pair(Int.MAX_VALUE, Int.MAX_VALUE)
                var max = Pair(Int.MIN_VALUE, Int.MIN_VALUE)
                for (elf in allElves) {
                    min = Pair(min.first.coerceAtMost(elf.pos.first), min.second.coerceAtMost(elf.pos.second))
                    max = Pair(max.first.coerceAtLeast(elf.pos.first), max.second.coerceAtLeast(elf.pos.second))
                }
                val total = (max.first - min.first + 1) * (max.second - min.second + 1)
                println("total: $total")
                println("Area - elf: ${total - allElves.size}")

                break
            }
            times += 1
            allPositions = mutableMapOf()
            direction = (direction + 1) % 4
            for (pair in proposedMoves.keys) {
                val list = proposedMoves[pair]!!
                    if (list.size > 1) {
                        //no move
                        for (pair2 in list) {
                            allPositions.putIfAbsent(pair2.second.pos.first, mutableMapOf())
                            allPositions[pair2.second.pos.first]!![pair2.second.pos.second] = pair2.second
                        }
                    } else {
                        allPositions.putIfAbsent(pair.first, mutableMapOf())
                        list.first().second.pos = pair
                        allPositions[pair.first]!![pair.second] = list.first().second

                    }
                }
//            println("Moved to")
//            allElves.forEach {
//                println(it.pos)
//            }
            }
        }
    }

// 6320 too high

// P2 987 too low