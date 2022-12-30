package day22

import helpers.ReadFile

class Day22 {
    val rlines = ReadFile.named("src/day22/input.txt")
    val tlines = listOf(
"        ...#",
"        .#..",
"        #...",
"        ....",
"...#.......#",
"........#...",
"..#....#....",
"..........#.",
"        ...#....",
"        .....#..",
"        .#......",
"        ......#.",
    )
    val tmove = "10R5L5R10L4R5L5"
    val rmove = "14R42L38R43L9L2L14L11R9R46R35R37L33R38R10R47R17L46L31R35L18R50R1R34R15L38L29L22L4R35R1R31R4L6R27L35L17L50L24L33R21L30R42L34R4R36R7L13R6R25L18R26R21L21R19R23R15R40R24L13R5L8L37R24R2R50L15L20R30R23L11L13R8R1R31L38L12R15R10L32R48L18R5R26L35R23L1L7R44L5L11L23L43L17R33R40L36R21R42R39R34L28L41R8R40R38R1L3L35R50L32R24R11R50L43R19R28R20L29R39R5R48L37L16L47R14R15R23L11L9R19L36R37R5R36R19R41L2L10R12R35L40L22R23R19L14R15R3R48L4L3L46L22L39R43L20L27L15R19R41L40R3R32R28R19L20R24L2L2L44L13L40L26L40L35L49R39R37R5L6R31L39L44L50R9L26R49L42L1L41L24R7L44L11L18L27L38R8L48R6R5R26R15R38L16L14L31L33L31L4R24L23R15R8L12R6L49L12R20L11L44L26L40R22R6L42R49L33L6R33R43R2L11R13R28L23L12R47R16L17R21L47L3L8R19R33L37R46R41R45L26L22R43L37R26R22R45R2L26R32R23L7R20R48R4R15L2L34R24R17R14R10L18R48R42R34R36L3R10R40L1R25R29R1L17L49L2R40L50R1L43L16L24R28L14R47R12R1R9L2L50R37L41L5L12L50R32L18L35R11L39L20R32L20R24L26L1L46R38R38R23L24R3L5L42L24R46R29R22L20L44L25R9L47L42R11L23L37L26R44L28L8R13L12R5L46L46L17R35L20L45R35L43R11R10R40R27L11L45R41L23L38L38L17R43R35R46R49R15L18L36L20R2R21R36L37R50L5L41L29L29R45R34L17L31R4L9R32R34R20L36L4L3L36L32R6L31R6R28R9L28R33L32R30L3L27L17R16L15L47R43L20R26R5L39R43L38L5L49R29L19R22R38L21L9R20L16R7R14R20R37L14L10L12L9R3L49L46L23R46L30R28R21L20R38L36L46L26L40R45R4R42L50L34R23R16L26L45L26L2L35L43R34L11L28L4L17L4L40R40R1L34R40R47L34R22L37R37R40L14R45R13R8L33R30R22R43R17L8L49R12L30L37L8R43R4R27R19L48L20L40R34R3R43L49L32R1R25L16L32R14L49R19L9R50L22L31L25L13R12L32R2R8L42R29R41R17L21R9R20R42L29L17L39R31L4R22L48R31L49R6R5L38L22L11L45L37R44R43R36R10R16R7R19L20L20R10R45L14L27L25R46R1L9R47L46L41R37R6R12L17L10L9R45L42R3R8L15R12R33L34L4R34R50L50R27R30R10L9R5R36R5R23L33R19R30R33R23L10R34L8R24R43L4R43L47R12L30R39R32L42L7L22L37R34L31R22R7L5R8R33L46R28L7L18L5R22R49L30L37R15R12L42L7R3L6R48L32R46L3L48L19L11R22L10L29R40R23R26L39L22R20L35L7L47R3L16L24L19R13L39L29L28R15R31L13R30L32L5R31L27L6R25R18R35L36L36L39L15L8L21R14R7L29L35L31L36L4R3L4R20L33R11L3R17L43L24L38R44R13R22R25R5L23R5R9R26L36L30R42L14R12R45R3R36L25L11L15R12R43R2L39R16R20L12L29L16L33R32R3L2L33R22L2R40R34L25L45R34R27L38L35L6R46R3R48R44R45L49L19R21L21L34R37L15R25R2R12L4R9R16R45R25R23L8L26L15R11R47R43L41L36R10L9L15L10R13L9L3L31L7L42R46R9R45R26L48L3L29L15L46L38L44R22R22L29R24L18L1L32R38L45R18L25R41R35L13R9L22L21L9L49R41L11R42R22R50L17L33R29R31R1R1R6R9L30R37R28L45L47L41R7R21L36R2R15R39R8L34R31R27L21L32L13R41R28R17L50R30R29R7L33R1R37R35L3R9R29R6L23R17R38R49L13R29L27R26R32L43R7R47L38L15R32R28R36R34L34R41R47L1L26L13L47L19L8L3R16R44R40R24R36R14R41R15R49L30L1R17R4L7L17R43R22L9R10L14L28R48L33R3R47R41L22R3R13L41L46R42L42R13R48R27R20L10L12L23R48R7R18R8R28L3R14L31R18L31R7L7R17L36R29L44R23L26L42R6L1R5R17L24L12R42R15R37R35L9R3L40R47L8R37L5R45R28L7L49L34L27R43R20R28R14L25R40R27R39R50R4L46R47R28L24L22R8R14L16L33R42R21L5L34R2L49L28R31R23L4R41R41R3L41L32R28R16R38L9L29R28L37R26R40R27R40R1R32R31L17R36R7R21L47R42R39R50L20L1R49L18L27R41R29R24R26R6R27L50R19R22R26L38R21R47L45L24R48R36L27R38R5L10R4L9L14L26R5L40L49L17L12L19R50L20L20L27R35L28L49L32L45R31L11L50R12L5L9R41R16R42L37L19R28R38L44L6L4L21R9L34L38R6L12R25L44R48L48R44L40R11L10L16R15R7L3R32R39R33L1L16R36R30L23L19R38R10R14R38L50L18R49L22L28L22R8R48R38L18L2R39L34L2R14L4L15R43R45R4L36R42L8R5L39L35R25R22R31L26R9R20R7L1R48L49R36R38R29R49R35R34R25L8R30L12L4L2R39L16L6R19L48R31R43R25L27R12L21L13L1L26L48R18R33L11L16L46L21L7L38L37R4L3R34R40R20R50R32R46L32L15L4L46R39R2L13R3R7L40R49R42R7R40R50R38R31R34R24L26R48R34R8R25R21L3R48L42R47R25R37L48R31L33L34L30R21R32R6R45L50R38R43R6R50L34R12L20L5R41R41L40R25L8L15R9R28L5L27R24R44R7R41L38L38R11L17L2L44R23L34L18L4R48L28R42L17L38R17R38L31L1L38R6L44R2L35L46R10R4R28R1L31R4R2R31L34L11R1L6L44L46L42R35R18R21R33R32R38R23R40L30R34L47L49L34L39R31L13L7R20L23L18L4L24R48L31L3L24R35L36R45L12R15L30R14L41L29R11R23R47R44L24L21R34R12R46L26L11L24R37L38L35R23L7L50R9R46R24L30R33R19L2R24L13R7R35L43L15L27L25R4L16L50L16R23L50R23R27L36R2L8L29L27L8R24L35L37R27L41L4L36R18L34L2L23R30R10R26L10R48L35R43L2R43L48R14R40L23R8R18R38L47L4R46L43R43L44L29R36L24L39R23R10R44R47R24L21R14R21L16L40L36R30L2L37L10L39L33L39R40R21R32L12L30R41L45L34R29R45L14R6R11R45R36L29L22L1L4R35R48R16R26L6R8L1L26L37R50L9R23R29L32L16L1L39R1L22R12L37R34L47R47R16L34L16R25R47L5L4R14R24L21L23L26L39R26L14L15L23L21L36R9L10R30L2R8R11R12R12R47L49L18R46R6L37R14R18L47R48R4L28L49L4L19R17R25R11L2R45L37R1L4R8L41R26L31R44R39R49L8R34L20L15L50L7L43L36R42R7R32L4R11L24R40R25L27L23L31R25L22R21L44L14L5R6L2R37L31R38R49L41L40R30L35L21L46R7R49L37L42R12R10L8L37R1R20R10L40R27R24L19L42L23L40R14R20R46R33L1L16L40L33R33L30R28L4R10R13R50R38R2L41R38R31L6L8R13L6L8L41L11L35L32R36L6L15R42R17R38L20R17R22R13R15R45L7R32L39R1R10R37L29L11L9L28L16L3R34R11L44L11L38L38R46L34R14L27L48R17L28L1L44R33L48L36R2L33L47L17R6R26L17L3L8R14L3R36R9L20R28R5R16R26R18L8R45R17R48R39R9L49L1L46R39L16R43L24L43R30L38R38R33R25R22L17R40L43L27L18R42L28L45L46R8L22L10L23L47L50L16R41R40R48R18R23L21R35L44L8L6R39L42L50L45L9R14R42L48R35L12R27L19L28R30L10L40R26R8R16L32L42R48R2L32L13R25R20L18R2R34R33L14R42L34L48L48R33R47L9L5L22L43R33R8L10R35R1R15R42R14L40L34L24R47L36L26R19R23R39R23R33R12R18R50L20L44R8L31L29R5L1R36L25R13L40R43L8L38R16L22R26L37L21L3L42L16R46R24R25R23R25L7L15L22L21R15L41L12R49R35R4R43L4R35L21R29R30R47R32R34R22R7R29R7R29R24R6R46R28L8R42R12L8L37R40R45R24L6R50R14L6R26L2L28R16L45L11R40L7L6L4L33R43R3R44L50R46R4R11R21L40R27L13R33R10L17R48R9L33L3R31L38R12L35R25R50L23L46L16R19L18R17L9L20L14L25R28R30L17L13R12L32R27L44L6L28L15L38L35L5R28L22L30L36L47L11L19R46R31L12L17L14R17R39L9L36L43R28R21R48R47R44R20L4R20L46R21R37R49L34R22R17L39L23R34L24R45R35R21R30L20R22R23R5L13L14R18L9R39L23L45L5R20L35L2L40R37R15R6R24L27L10L38R17L26L39R22R6L19L48L20R41R48R45L40L29R14L45L40L5R1L12R50"
val lines = rlines.map { it.split("").filter { it.isNotEmpty() } }
var move = rmove
    class Block {
        val pos: Pair<Int, Int>
        var wall: Boolean? = null
        var empty: Boolean? = null
        var left: Block? = null
        var right: Block? = null
        var top: Block? = null
        var bottom: Block? = null

        var rightSpecial: Direction? = null
        var leftSpecial: Direction? = null
        var topSpecial: Direction? = null
        var bottomSpecial: Direction? = null

        public fun move(direction: Direction): Pair<Block, Direction> {
            when (direction) {
                Direction.LEFT -> (if (left!!.wall == true) return Pair(this, direction) else return Pair(left!!, if (leftSpecial != null) leftSpecial!! else direction))
                Direction.RIGHT -> if (right!!.wall == true) return Pair(this, direction) else return Pair(right!!, if (rightSpecial != null) rightSpecial!! else direction)
                Direction.TOP -> if (top!!.wall == true) return Pair(this, direction) else return Pair(top!!, if (topSpecial != null) topSpecial!! else direction)
                Direction.BOTTOM -> if (bottom!!.wall == true) return Pair(this, direction) else return Pair(bottom!!, if (bottomSpecial != null) bottomSpecial!! else direction)
            }
        }
        constructor(pos: Pair<Int, Int>) {
            this.pos = pos
        }
    }

    enum class Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }
//    r d l u
    fun valueo(direction: Direction) : Int {
        return when (direction) {
            Direction.RIGHT -> 0
            Direction.BOTTOM -> 1
            Direction.LEFT -> 2
            Direction.TOP -> 3
        }
    }
    class Move {
        val right: Boolean?
        val steps: Int
        constructor(right: Boolean?, steps: Int) {
            this.right = right
            this.steps = steps
        }
    }
    fun turn(direction: Direction, right: Boolean): Direction {
        return when (direction) {
            Direction.LEFT -> if (right) Direction.TOP else Direction.BOTTOM
            Direction.RIGHT -> if (right) Direction.BOTTOM else Direction.TOP
            Direction.TOP -> if (right) Direction.RIGHT else Direction.LEFT
            Direction.BOTTOM -> if (right) Direction.LEFT else Direction.RIGHT
        }
    }
    fun result1() {
//        println("  #.".split("").filter { it.isNotEmpty() })
//        return
        var blocks = mutableListOf<List<Block>>()
        for (line in lines.indices) {
            blocks.add((lines[line].indices).map {
                Block(Pair(line, it))
            })

        }
        for (line in lines.indices) {
            val chars = lines[line]
            for (char in chars.indices) {
                if (chars[char] == " ") {
                    blocks[line][char].empty = true
                    continue
                }
                if (chars[char] == "#") {
                    blocks[line][char].wall = true
                }

                var leftIndex = (chars.size + char - 1) % chars.size
                while (chars[leftIndex] == " ") {
                    leftIndex = (chars.size + leftIndex - 1) % chars.size
                }
                blocks[line][char].left = blocks[line][leftIndex]

                var rightIndex = (char + 1) % chars.size
                while (chars[rightIndex] == " ") {
                    rightIndex = (rightIndex + 1) % chars.size
                }
                blocks[line][char].right = blocks[line][rightIndex]

                var topIndex = (lines.size + line - 1) % lines.size
                while (lines[topIndex].size <= char || lines[topIndex][char] == " ") {
                    topIndex = (lines.size + topIndex - 1) % lines.size
                }
                blocks[line][char].top = blocks[topIndex][char]

                var bottomIndex = (lines.size + line + 1) % lines.size
                while (lines[bottomIndex].size <= char || lines[bottomIndex][char] == " ") {
                    bottomIndex = (lines.size + bottomIndex + 1) % lines.size
                }
                blocks[line][char].bottom = blocks[bottomIndex][char]

            }
        }

        // Folding

        //l49 100 - 149  -> l50 - l99 C99
        //L100 c0-49 -> l50 - 99 c50
        //l49-0 c50 -> l100-149 c0
        //l0-49 c149 -> l149-100 c99
        //L 150-199 c49 -> l149 c50-99
        //l 150-199 c0 -> l0 c50-99
        // l 199 c0-49 -> l0 c100-149


        for (i  in 0 until 50) {
            // First
            blocks[49][100+i].bottom = blocks[50+i][99]
            blocks[49][100+i].bottomSpecial = Direction.LEFT
            blocks[50+i][99].right = blocks[49][100+i]
            blocks[50+i][99].rightSpecial = Direction.TOP
            // Second
            blocks[100][i].top = blocks[50+i][50]
            blocks[100][i].topSpecial = Direction.RIGHT
            blocks[50+i][50].left = blocks[100][i]
            blocks[50+i][50].leftSpecial = Direction.BOTTOM
            // Third
            blocks[49-i][50].left = blocks[100+i][0]
            blocks[49-i][50].leftSpecial = Direction.RIGHT
            blocks[100+i][0].left = blocks[49-i][50]
            blocks[100+i][0].leftSpecial = Direction.RIGHT
            // Fourth
            blocks[0+i][149].right = blocks[149-i][99]
            blocks[0+i][149].rightSpecial = Direction.LEFT
            blocks[149-i][99].right = blocks[0+i][149]
            blocks[149-i][99].rightSpecial = Direction.LEFT
            // Fifth
            blocks[150+i][49].right = blocks[149][50+i]
            blocks[150+i][49].rightSpecial = Direction.TOP
            blocks[149][50+i].bottom = blocks[150+i][49]
            blocks[149][50+i].bottomSpecial = Direction.LEFT
            // Sixth
            blocks[150+i][0].left = blocks[0][50+i]
            blocks[150+i][0].leftSpecial = Direction.BOTTOM
            blocks[0][50+i].top = blocks[150+i][0]
            blocks[0][50+i].topSpecial = Direction.RIGHT
            // Seventh
            blocks[199][i].bottom = blocks[0][100+i]
            blocks[199][i].bottomSpecial = Direction.BOTTOM
            blocks[0][100+i].top = blocks[199][i]
            blocks[0][100+i].topSpecial = Direction.TOP
        }

        var path = mutableListOf<Move>()
//10R5L5R10L4R5L5
        while (move.isNotEmpty()) {
            val steps = move.takeWhile { it.isDigit() }.toInt()
            move = move.dropWhile { it.isDigit() }
            val turn = if (move.isNotEmpty()) move.first() == 'R' else null
            path.add(Move(turn, steps))
            move = move.drop(1)
        }

        var currentBlock = blocks[0].first { it.empty != true }
        var direction = Direction.RIGHT
        for (move in path) {
            if (currentBlock.empty == true) {
                throw Exception("Whoops")
            }
            for (i in 0 until move.steps) {
                val r = currentBlock.move(direction)
                currentBlock = r.first
                direction = r.second
            }
            if (move.right != null) {
                direction = turn(direction, move.right)
            }
            println(currentBlock.pos)
        }

        for (line in blocks.indices) {
            for (char in blocks[line].indices) {
                if (blocks[line][char] == currentBlock) {
                    val total = ((line + 1) * 1000) + ((char + 1) * 4) + valueo(direction)
                    println("Found: $total")
                }
            }
        }
    }
}
// too low 50454, 117330, is: 147245

