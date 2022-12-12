package day12

import helpers.ReadFile

class Block {
    var minDistanceFromOrigin = Int.MAX_VALUE
    var pathOfMinDistance: List<Pair<Int, Int>> = listOf()
    val isEnd: Boolean
    val isStart: Boolean
    val height: Int
    var position: Pair<Int, Int> = Pair(0, 0)
    var validEdges: List<Pair<Int, Int>> = listOf()


    constructor(isEnd: Boolean, isStart: Boolean, height: String) {
        this.isEnd = isEnd
        this.isStart = isStart
        var h = height.toCharArray().first().code - 96
        if (isStart) {
            h = 1
        }
        if (isEnd) {
            h = 26
        }
        this.height = h
    }
}
class Day12 {
    val lines = ReadFile.named("src/day12/input.txt")
    val linesT = listOf(
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi",
    )
    val chars = lines.map { it.split("").filter { it.isNotEmpty() }.map { char ->
        Block(isEnd = char == "E", isStart = char == "S", height = char)
    } }

    fun pairsNextTo(pair: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (x, y) = pair
        var pairs: MutableList<Pair<Int, Int>> = mutableListOf()
        if (x != 0) {
            pairs.add(Pair(x - 1, y))
        }
        if (x != chars[0].size - 1) {
            pairs.add(Pair(x + 1, y))
        }
        if (y != 0) {
            pairs.add(Pair(x, y - 1))
        }
        if (y != chars.size - 1) {
            pairs.add(Pair(x, y + 1))
        }
        return pairs
    }
    fun result1() {
        var S: Pair<Int, Int> = Pair(0, 0)
        var E: Pair<Int, Int> = Pair(0, 0)

        for (line in 0 until chars.size) {
            for (char in 0 until chars[line].size) {
                val currentChar = chars[line][char]
                val position = Pair(char, line)
                currentChar.position = position
                currentChar.validEdges = pairsNextTo(position).filter {
                    val candidate = chars[it.second][it.first]
                    !candidate.isEnd && candidate.height+1 >= currentChar.height

                }
                if (currentChar.isEnd) {
                    currentChar.minDistanceFromOrigin = 0
                    currentChar.pathOfMinDistance = listOf(position)
                    E = position
                }
                if (currentChar.isStart) {
                    S = position
                }
            }
        }
        val currentLayer = listOf(chars[E.second][E.first])
        breadthFirstSearch(currentLayer)

        print(chars[S.second][S.first].minDistanceFromOrigin)
        print(chars[S.second][S.first].pathOfMinDistance)

        var min = chars[S.second][S.first]
        for (line in 0 until chars.size) {
            for (char in 0 until chars[line].size) {
                val currentChar = chars[line][char]
                if (currentChar.minDistanceFromOrigin < min.minDistanceFromOrigin && currentChar.height == 1) {
                    min = currentChar
                }

            }
        }
        println("min: ${min.minDistanceFromOrigin}")
    }
//    Sabqponm
//    abcryxxl
//    accszExk
//    acctuvwj
//    abdefghi
//
//
//    v..v<<<<
//    >v.vv<<^
//    .>vv>E^^
//    ..v>>>^^
//    ..>>>>>^


    fun breadthFirstSearch(currentLayer: List<Block>) {
        var nextLayer = mutableListOf<Block>()
        for (current in currentLayer) {
            for (location in current.validEdges) {
                val block = chars[location.second][location.first]
                val pathIsShortestFollowable = block.minDistanceFromOrigin > current.minDistanceFromOrigin + 1
                if (pathIsShortestFollowable) {
                    block.minDistanceFromOrigin = current.minDistanceFromOrigin + 1
                    block.pathOfMinDistance = current.pathOfMinDistance + block.position
                    if (!block.isStart) {
                        nextLayer.add(block)
                    }

                }

            }
        }

        println(nextLayer.map{it.position})
        if (currentLayer.size > 0) {
            breadthFirstSearch(nextLayer)
        }
    }

}