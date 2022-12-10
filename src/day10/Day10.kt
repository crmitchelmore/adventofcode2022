package day10

import helpers.ReadFile

class Day10 {

    val linesr = ReadFile.named("src/day10/input.txt")
    val lines = listOf(
    "addx 15",
    "addx -11",
    "addx 6",
    "addx -3",
    "addx 5",
    "addx -1",
    "addx -8",
    "addx 13",
    "addx 4",
    "noop",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx 5",
    "addx -1",
    "addx -35",
    "addx 1",
    "addx 24",
    "addx -19",
    "addx 1",
    "addx 16",
    "addx -11",
    "noop",
    "noop",
    "addx 21",
    "addx -15",
    "noop",
    "noop",
    "addx -3",
    "addx 9",
    "addx 1",
    "addx -3",
    "addx 8",
    "addx 1",
    "addx 5",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx -36",
    "noop",
    "addx 1",
    "addx 7",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "addx 6",
    "noop",
    "noop",
    "noop",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx 7",
    "addx 1",
    "noop",
    "addx -13",
    "addx 13",
    "addx 7",
    "noop",
    "addx 1",
    "addx -33",
    "noop",
    "noop",
    "noop",
    "addx 2",
    "noop",
    "noop",
    "noop",
    "addx 8",
    "noop",
    "addx -1",
    "addx 2",
    "addx 1",
    "noop",
    "addx 17",
    "addx -9",
    "addx 1",
    "addx 1",
    "addx -3",
    "addx 11",
    "noop",
    "noop",
    "addx 1",
    "noop",
    "addx 1",
    "noop",
    "noop",
    "addx -13",
    "addx -19",
    "addx 1",
    "addx 3",
    "addx 26",
    "addx -30",
    "addx 12",
    "addx -1",
    "addx 3",
    "addx 1",
    "noop",
    "noop",
    "noop",
    "addx -9",
    "addx 18",
    "addx 1",
    "addx 2",
    "noop",
    "noop",
    "addx 9",
    "noop",
    "noop",
    "noop",
    "addx -1",
    "addx 2",
    "addx -37",
    "addx 1",
    "addx 3",
    "noop",
    "addx 15",
    "addx -21",
    "addx 22",
    "addx -6",
    "addx 1",
    "noop",
    "addx 2",
    "addx 1",
    "noop",
    "addx -10",
    "noop",
    "noop",
    "addx 20",
    "addx 1",
    "addx 2",
    "addx 2",
    "addx -6",
    "addx -11",
    "noop",
    "noop",
    "noop",
    );

    fun result1() : String {


        val l = listOf("noop",
        "addx 3",
        "addx -5")
        val adds: MutableList<Int> = linesr.flatMap {
            val cmd = it.split(" ")
            val add = when (cmd[0]) {
                "addx" -> listOf(0,cmd[1].toInt())
                "noop" ->  listOf(0)
                else -> throw Exception("Unknown command")
            }
            add
        }.toMutableList()
        adds.add(0,1)
        var x = 0
        val combined = adds.map {
            x += it
            x
        }
        println(adds)
        println(combined)
        var i = 19
        var output = mutableListOf<Int>()
        while (i < combined.size) {
            output.add(combined[i] * (i+1))
            i+=40
        }
        println(output)
//        return output.sum().toString()
        var pixels = mutableListOf<String>()
        for (cycle in 0 until combined.size) {
            val position = (cycle + 1) % 40
            val pixel = if ( ((position-2)..(position)).contains(combined[cycle])) "#" else "."
            pixels.add(pixel)
            print(pixel)
            if (position % 40 == 0) {
                println()
            }
        }
        return ""
    }
}
//##..##..##..##..##..##..##..##..##..##..
//###...###...###...###...###...###...###.
//####....####....####....####....####....
//#####.....#####.....#####.....#####.....
//######......######......######......####
//#######.......#######.......#######.....


//##..##..#..##...##.##..##..##..##..##..#
//###..####..###...###...####..####..###..
//##.....#####...###.....####.....##.....#
//####.....##.#......#####.....####.......
//###.##.....##.###......######......##.#.
//###.##.......####.#........########.....

//###...##..###..#..#.####.#..#.####...###
//#..#.#..#.#..#.#.#..#....#.#..#.......##
//#..#.#..#.#..#.##...###..##...###.....##
//###..####.###..#.#..#....#.#..#.......#.
//#....#..#.#....#.#..#....#.#..#....#..#.
//#....#..#.#....#..#.#....#..#.####..##..