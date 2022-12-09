package day9

import helpers.ReadFile
import kotlin.math.absoluteValue

class Day9 {

    val lines = ReadFile.named("src/day9/input.txt")
//    val lines = listOf(
//                "R 4",
//                "U 4",
//                "L 3",
//                "D 1",
//                "R 4",
//                "D 1",
//                "L 5",
//                "R 2"
//    )
//    val lines = listOf(
//    "R 5",
//            "U 8",
//            "L 8",
//            "D 3",
//            "R 17",
//            "D 10",
//            "L 25",
//            "U 20",
//    )

    fun result1() : String {
        var visited = mutableListOf(Pair(0, 0))
        // X.Y
        var snake = mutableListOf(Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0),Pair(0, 0))

        for (line in lines) {
            val chunks = line.split(" ")
            val direction = chunks[0]
            val distance = chunks[1].toInt()
            println("direction: $direction, distance: $distance")

            for (i in 1..distance) {
                var head = snake[0]
                when (direction) {
                    "U" -> head = Pair(head.first, head.second + 1)
                    "D" -> head = Pair(head.first, head.second - 1)
                    "L" -> head = Pair(head.first - 1, head.second)
                    "R" -> head = Pair(head.first + 1, head.second)
                }
                snake[0] = head
                for (segment in 1 until snake.size) {
                    var head = snake[segment-1]
                    var tail = snake[segment]
                    if ((head.first - tail.first).absoluteValue > 1 || (head.second - tail.second).absoluteValue > 1) {
                        snake[segment] = Pair(tail.first + if (head.first == tail.first) 0 else (if (head.first > tail.first) 1 else -1), tail.second + if (head.second == tail.second) 0  else (if (head.second > tail.second) 1 else -1))
                        if (segment == snake.size -1) {
                            visited.add(snake[segment])
                        }
                    } else {
                        break
                    }
                }
                println(snake)
            }

        }
        return visited.toSet().size.toString()
    }
}