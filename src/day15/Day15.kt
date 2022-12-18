package day15

import org.w3c.dom.ranges.Range
import java.lang.Integer.max
import java.lang.Integer.min

class Day15 {
    val testInput = listOf(
        Pair(Pair(2, 18),Pair(-2, 15)),
        Pair(Pair(9, 16),Pair(10, 16)),
        Pair(Pair(13, 2),Pair(15, 3)),
        Pair(Pair(12, 14),Pair(10, 16)),
        Pair(Pair(10, 20),Pair(10, 16)),
        Pair(Pair(14, 17),Pair(10, 16)),
        Pair(Pair(8, 7),Pair(2, 10)),
        Pair(Pair(2, 0),Pair(2, 10)),
        Pair(Pair(0, 11),Pair(2, 10)),
        Pair(Pair(20, 14),Pair(25, 17)),
        Pair(Pair(17, 20),Pair(21, 22)),
        Pair(Pair(16, 7),Pair(15, 3)),
        Pair(Pair(14, 3),Pair(15, 3)),
        Pair(Pair(20, 1),Pair(15, 3)),
    )
    //Sensor=(8, 7) r: (2, 10) distanceToClosest=9, distanceToY=3, length=6 range=2..14
    val input = listOf(
        Pair(Pair(3889276, 3176133), Pair(3738780, 3090050)),
        Pair(Pair(3545888, 1389980), Pair(3687798, 2823020)),
        Pair(Pair(2887269, 2488344), Pair(2809378, 2513386)),
        Pair(Pair(3990278, 43134),   Pair(2307159, 135337)),
        Pair(Pair(3746631, 2990632), Pair(3738780, 3090050)),
        Pair(Pair(7523,    59064),   Pair(278652, -182407)),
        Pair(Pair(2662631, 3349709), Pair(2294322, 3429562)),
        Pair(Pair(3999326, 3030235), Pair(3738780, 3090050)),
        Pair(Pair(2788203, 3722031), Pair(3009520, 4176552)),
        Pair(Pair(1872146, 1228203), Pair(1213036, 1428271)),
        Pair(Pair(231045,  2977983), Pair(-362535, 2000000)),
        Pair(Pair(2233881, 421153),  Pair(2307159, 135337)),
        Pair(Pair(3915820, 2609677), Pair(3687798, 2823020)),
        Pair(Pair(2959514, 2529069), Pair(2809378, 2513386)),
        Pair(Pair(1829825, 2614275), Pair(2809378, 2513386)),
        Pair(Pair(1031015, 2036184), Pair(1213036, 1428271)),
        Pair(Pair(3894267, 3758546), Pair(3738780, 3090050)),
        Pair(Pair(2653530, 445121),  Pair(2307159, 135337)),
        Pair(Pair(1528274, 1670020), Pair(1213036, 1428271)),
        Pair(Pair(3839068, 2974837), Pair(3738780, 3090050)),
        Pair(Pair(254225,  9603),    Pair(278652, -182407)),
        Pair(Pair(2214848, 3333326), Pair(2294322, 3429562)),
        Pair(Pair(1008775, 292264),  Pair(278652, -182407)),
        Pair(Pair(2072077, 6712),    Pair(2307159, 135337)),
        Pair(Pair(3344028, 3459786), Pair(3738780, 3090050)),
        Pair(Pair(984627,  3991112), Pair(2294322, 3429562)),
        Pair(Pair(198206,  2034713), Pair(-362535, 2000000)),
        Pair(Pair(460965,  1150404), Pair(1213036, 1428271)),
        Pair(Pair(2198999, 3584784), Pair(2294322, 3429562)),
        Pair(Pair(3212614, 2899682), Pair(3687798, 2823020)),
        Pair(Pair(3797078, 2864795), Pair(3687798, 2823020)),
        Pair(Pair(2465051, 2871666), Pair(2809378, 2513386)),
        Pair(Pair(2356218, 3981953), Pair(2294322, 3429562)),
        Pair(Pair(2389861, 1856461), Pair(2809378, 2513386)),
        Pair(Pair(2852352, 2506253), Pair(2809378, 2513386)),
        Pair(Pair(2275278, 742411),  Pair(2307159, 135337)),
        Pair(Pair(1562183, 3626443), Pair(2294322, 3429562)),
        Pair(Pair(44398,   534916),  Pair(278652, -182407)),
    )

    fun cDist(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        return Math.abs(a.first - b.first) + Math.abs(a.second - b.second)
    }

    fun intersectRange(s: Pair<Int, Int>, r: Pair<Int, Int>, distanceToClosest: Int, y: Int): IntRange? {
        val distanceToY = Math.abs(s.second - y)
        val length = distanceToClosest - distanceToY
        if (length < 0) {
//            println("Sensor=$s r: $r distanceToClosest=$distanceToClosest, distanceToY=$distanceToY, length=$length NULL")
            return null
        }
        val range = s.first - length .. s.first + length
//        println("Sensor=$s r: $r distanceToClosest=$distanceToClosest, distanceToY=$distanceToY, length=$length range=$range")
        return range
    }
    fun result1() {

        val test = Pair(testInput, 10)
        val real = Pair(input, 2000000)
        var (ip, testLine) = real
        for (a in 0 .. 4000000) {
            var ranges = mutableListOf<IntRange>()
            if (a % 10000 == 0) {
                println("a=$a")
            }
            testLine = a
            for (line in ip) {
                val n = intersectRange(line.first, line.second, cDist(line.first, line.second), testLine)
                if (n != null) {
                    ranges.add(n)
                }
            }

            var merged = true
            while (merged) {
                merged = false
                for (i in 0 until ranges.size) {
                    for (range2 in ranges) {
                        if (range2 != ranges[i] && (ranges[i].contains(range2.first - 1) || ranges[i].contains(range2.last - 1))) {
                            ranges[i] = (min(range2.first, ranges[i].first)..max(range2.last, ranges[i].last))
                            merged = true
                        }
                    }
                }
                var s = ranges.toMutableSet()
                var toRemove = mutableListOf<IntRange>()
                for (r in s) {
                    for (r2 in ranges) {
                        if (r != r2 && r.first <= r2.first && r.last >= r2.last) {
                            toRemove.add(r2)
                        }
                    }
                }
                for (r in toRemove) {
                    s.remove(r)
                }
                ranges = s.toMutableList()
            }
            if (ranges.size > 1) {
                println("a=$a")
                println(ranges)
                break
            }
//            println("Final Ranges: ${ranges.toSet()}")
//            var total = 0
//            ranges.toSet().forEach() { range ->
//                total += (range.last() - (range.first() - 1))
//            }
//            var removeBeacons = mutableSetOf<Pair<Int, Int>>()
//            for (line in ip) {
//                if (line.second.second == testLine) {
//                    if (ranges.filter { it.contains(line.second.first) }.count() == 1) {
//                        removeBeacons.add(line.second)
//                    }
//
//                }
//            }
//            println("Remove beacons: $removeBeacons")
//            total -= removeBeacons.size
//            println("Result 1: $total")
        }
    }
}

// Tried = 4514453
// Tried = 4514452
