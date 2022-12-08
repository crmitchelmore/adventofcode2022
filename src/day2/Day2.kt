package day2

import helpers.ReadFile

class Day2 {
//    val lines = listOf(
//            "A X",
//            "A Y",
//            "A Z",
//            "B X",
//            "B Y",
//            "B Z",
//            "C X",
//            "C Y",
//            "C Z",
//        )
//    val lines = listOf(
//    "A Y",
//            "B X",
//            "C Z"
//    )
        val lines = ReadFile.named("src/day2/input.txt")
    var points = hashMapOf(
        "X" to 0,
        "Y" to 1,
        "Z" to 2,
        "A" to 0,
        "B" to 1,
        "C" to 2,
    )
    var reversePoints = hashMapOf(
        0 to "X",
        1 to "Y",
        2 to "Z",
    )
    var names = hashMapOf(
        "X" to "rock",
        "Y" to "paper",
        "Z" to "scissors",
        "A" to "rock",
        "B" to "paper",
        "C" to "scissors",
    )

    var wld = hashMapOf(
        0 to "lost",
        3 to "draw",
        6 to "won",
    )
    var winPoints: (String, String) -> Int = { a, b ->
        if (points[a] == 2 && points[b] == 0) {
            6;
        } else if(points[a]==points[b]) {
            3;
        } else {
            if (points[a]!! + 1 == points[b]!!) 6 else 0;
        }}

    fun result1(): String {
//

        return lines.fold(0) { a: Int, b: String ->
            var r = b.split(" ")
            var p1 = r[0]
            var p2 = r[1]
            println("L A: ${names[p1]} B: ${names[p2]} score: ${points[p2]!!}  res: ${wld[winPoints(p1, p2)]}")
            a + points[p2]!! + 1 + winPoints(p1, p2)
        }.toString()
    }

    fun result2(): String {
//        x lose y darw z win
        return lines.fold(0) { a: Int, b: String ->
            var r = b.split(" ")
            var p1 = r[0]
            var p2 = r[1]
            if (p2 == "Y") {
                p2 = p1
            } else if (p2 == "Z") {
                p2 = reversePoints[(points[p1]!! + 1) % 3]!!
            } else {
                p2 = reversePoints[(points[p1]!! + 2) % 3]!!
            }
            println("L A: ${names[p1]}(${p1}) B: ${names[p2]}(${p2}) score: ${points[p2]!! + 1}  res: ${wld[winPoints(p1, p2)]}(${winPoints(p1, p2)})")
            a + points[p2]!! + 1 + winPoints(p1, p2)
        }.toString()
    }
}