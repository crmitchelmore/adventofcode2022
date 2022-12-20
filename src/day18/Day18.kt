package day18

import helpers.ReadFile
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day18 {
    val rlines = ReadFile.named("src/day18/input.txt")
    val tlines = listOf(
        "2,2,2",
        "1,2,2",
        "3,2,2",
        "2,1,2",
        "2,3,2",
        "2,2,1",//
        "2,2,3",
        "2,2,4",
        "2,2,6",
        "1,2,5",
        "3,2,5",
        "2,1,5",
        "2,3,5",
    )
    val blines = listOf(
        "1,1,1",
        "2,1,1",
    )
    val lines = rlines.map {
        val point = it.split(",").map { it.toInt() }
        Triple(point[0], point[1], point[2])
    }

    var exposedFaces = mutableMapOf<Triple<Int,Int,Int>, MutableSet<String>>()
    fun tr(t: Triple<Int,Int, Int>, direction: String, index: Int) : Int {
        if (direction == "z") {
            when (index) {
                1 -> return t.first
                2 -> return t.second
                3 -> return t.third
            }
        } else if (direction == "y") {
            when (index) {
                1 -> return t.first
                2 -> return t.third
                3 -> return t.second
            }
        } else {
            when (index) {
                1 -> return t.third
                2 -> return t.second
                3 -> return t.first
            }
        }
        throw Exception("Bad")
    }
    fun sweep(direction: String) : Int {
        var mappedLines = mutableMapOf<Int, List<Triple<Int,Int, Int>>>()
        lines.forEach { mappedLines[tr(it, direction, 3)] = mappedLines.getOrDefault(tr(it, direction, 3), listOf()) + it }
        var numPairedFaces = 0
        val keys = mappedLines.keys.sorted()
        for (i in 1 until keys.size) {
            if (keys[i] - keys[i-1] == 1) {
                for (cube1 in mappedLines[keys[i-1]]!!) {
                    for (cube2 in mappedLines[keys[i]]!!) {
                        if (tr(cube1, direction, 1) == tr(cube2, direction, 1) && tr(cube1, direction, 2) == tr(cube2, direction, 2)) {

                            exposedFaces[cube1]!!.remove(if (direction == "z") "Top" else if (direction == "y") "Back" else "Right")
                            exposedFaces[cube2]!!.remove(if (direction == "z") "Bottom" else if (direction == "y") "Front" else "Left")
                            numPairedFaces++
                        }
                    }
                }
            }
        }
        return numPairedFaces
    }
    fun sharesEdge(cube1: Triple<Int,Int,Int>, cube2: Triple<Int,Int,Int>) : Boolean {
        return (( abs(cube1.first - cube2.first) == 1 && cube1.second == cube2.second && cube1.third == cube2.third ) ||
               ( cube1.first == cube2.first && abs(cube1.second - cube2.second) == 1 && cube1.third == cube2.third ) ||
               ( cube1.first == cube2.first && cube1.second == cube2.second && abs(cube1.third - cube2.third) == 1 )) ||
               (( abs(cube1.first - cube2.first) == 1 && abs(cube1.second - cube2.second) == 1  && cube1.third == cube2.third ) ||
                ( cube1.first == cube2.first && abs(cube1.second - cube2.second) == 1 && abs(cube1.third - cube2.third) == 1 ) ||
                ( abs(cube1.first - cube2.first) == 1  && cube1.second == cube2.second && abs(cube1.third - cube2.third) == 1 ) )

    }
    fun buildSurfaces(surfaceFaces: Set<Triple<Int, Int, Int>>) : Map<Int, List<Triple<Int,Int,Int>>> {
        var surfaceIndex = 0
        var cubeToSurfaceNum = mutableMapOf<Triple<Int,Int,Int>, Int>()
        var surfaceNumToCubs = mutableMapOf<Int, List<Triple<Int,Int,Int>>>()
        for (cube in surfaceFaces) {
            var foundExistingSurface = false
            // Check if this cube shares an edge with any existing surface
            for (surface in surfaceNumToCubs.keys) {
                for (surfaceCube in surfaceNumToCubs[surface]!!) {
                    if (sharesEdge(cube, surfaceCube)) {
                        cubeToSurfaceNum[cube] = surface
                        surfaceNumToCubs[surface] = surfaceNumToCubs[surface]!! + cube
                        foundExistingSurface = true
                        break
                    }
                }
                if (foundExistingSurface) {
                    break
                }
            }
            // If not, create a new surface
            if (!foundExistingSurface) {
                surfaceIndex++
                cubeToSurfaceNum[cube] = surfaceIndex
                surfaceNumToCubs[surfaceIndex] = listOf(cube)
            }
        }
        // Merge surfaces
        var merged = true
        while (merged) {
            merged = false

            for (surface1 in surfaceNumToCubs.keys) {
                for (surface2 in surfaceNumToCubs.keys) {
                    if (surface1 != surface2) {
                        for (cube1 in surfaceNumToCubs[surface1]!!) {
                            for (cube2 in surfaceNumToCubs[surface2]!!) {
                                if (sharesEdge(cube1, cube2)) {
                                    // Merge surface2 into surface1
                                    for (cube in surfaceNumToCubs[surface2]!!) {
                                        cubeToSurfaceNum[cube] = surface1
                                    }
                                    surfaceNumToCubs[surface1] = surfaceNumToCubs[surface1]!! + surfaceNumToCubs[surface2]!!
                                    surfaceNumToCubs.remove(surface2)
                                    merged = true
                                    break
                                }
                            }
                            if (merged) {
                                break
                            }
                        }
                        if (merged) {
                            break
                        }
                    }
                }
                if (merged) {
                    break
                }
            }
        }
        // Now merge in to true surfaces

        return surfaceNumToCubs
    }

    fun fill(lavas: Set<Triple<Int, Int, Int>>, x: Pair<Int, Int>, y: Pair<Int, Int>, z: Pair<Int, Int>) : Set<Triple<Int, Int, Int>> {
        var waters = mutableSetOf(Triple(x.first - 1, y.first - 1, z.first - 1))

        for (i in 1..4) {
            for (i in x.first - 1..x.second + 1) {
                for (j in y.first - 1..y.second + 1) {
                    for (k in z.first - 1..z.second + 1) {
                        if (lavas.contains(Triple(i, j, k))) {
                            continue
                        }
                        val neigh = listOf(
                            Triple(i + 1, j, k),
                            Triple(i - 1, j, k),
                            Triple(i, j + 1, k),
                            Triple(i, j - 1, k),
                            Triple(i, j, k + 1),
                            Triple(i, j, k - 1)
                        )
                        if (neigh.any { waters.contains(it) }) {
                            waters.add(Triple(i, j, k))
                        } else {
                            println("cave")
                        }
                    }
                }
            }
        }
        return waters
 }
    var numlavas = 0
    fun fild(lavas: Set<Triple<Int, Int, Int>>, x: Pair<Int, Int>, y: Pair<Int, Int>, z: Pair<Int, Int>) : Set<Triple<Int, Int, Int>> {
        var waters = mutableSetOf(Triple(x.first - 1, y.first - 1, z.first - 1))
        var items = mutableListOf(waters.first())

        while (items.size > 0) {
            val item = items.removeAt(0)

            val neigh = listOf(
                Triple(item.first + 1, item.second, item.third),
                Triple(item.first - 1, item.second, item.third),
                Triple(item.first, item.second + 1, item.third),
                Triple(item.first, item.second - 1, item.third),
                Triple(item.first, item.second, item.third + 1),
                Triple(item.first, item.second, item.third - 1)
            )
            for (n in neigh) {
                if (lavas.contains(n) ) {
                    numlavas += 1
                } else {
                    if (waters.contains(n) || n.first < x.first - 1 || n.first > x.second + 1 || n.second < y.first - 1 || n.second > y.second + 1 || n.third < z.first - 1 || n.third > z.second + 1) {
                        continue
                    }
                    items.add(n)
                    waters.add(n)
                }
            }
        }
        return waters
    }

    fun flood(lava: List<Triple<Int, Int, Int>>) : Set<Triple<Int,Int,Int>> {
        var minMaxX: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var minMaxY: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var minMaxZ: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        for (cube in lava) {
            minMaxX = Pair(min(minMaxX.first, cube.first), max(minMaxX.second, cube.first))
            minMaxY = Pair(min(minMaxY.first, cube.second), max(minMaxY.second, cube.second))
            minMaxZ = Pair(min(minMaxZ.first, cube.third), max(minMaxZ.second, cube.third))
        }
        // Be cautious about edges
        minMaxX = Pair(minMaxX.first, minMaxX.second)
        minMaxY = Pair(minMaxY.first, minMaxY.second)
        minMaxZ = Pair(minMaxZ.first, minMaxZ.second)
        val a = fild(lava.toSet(), minMaxX, minMaxY, minMaxZ)
        val b = fill(lava.toSet(), minMaxX, minMaxY, minMaxZ)
        print("Lavas $numlavas")
        return a
    }
    fun invertSurface(surface: List<Triple<Int, Int, Int>>): Set<Triple<Int, Int, Int>> {

        var minMaxX: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var minMaxY: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var minMaxZ: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        for (cube in surface) {
            minMaxX = Pair(min(minMaxX.first, cube.first), max(minMaxX.second, cube.first))
            minMaxY = Pair(min(minMaxY.first, cube.second), max(minMaxY.second, cube.second))
            minMaxZ = Pair(min(minMaxZ.first, cube.third), max(minMaxZ.second, cube.third))
        }
        // Be cautious about edges
        minMaxX = Pair(minMaxX.first, minMaxX.second)
        minMaxY = Pair(minMaxY.first, minMaxY.second + 1)
        minMaxZ = Pair(minMaxZ.first, minMaxZ.second + 1)
        var allCubes = mutableSetOf<Triple<Int, Int, Int>>()
        for (x in minMaxX.first until minMaxX.second) {
            for (y in minMaxY.first until minMaxY.second) {
                for (z in minMaxZ.first until minMaxZ.second) {
                    allCubes.add(Triple(x, y, z))
                }
            }
        }
        allCubes.removeAll(surface.toSet())
        return allCubes
    }
    fun result1() {
        for (line in lines) {
            exposedFaces[line] = mutableSetOf("Top", "Bottom", "Left", "Right", "Front", "Back")
        }

        val joinedFaces = sweep("x") + sweep("y") + sweep("z")
        val surfaceArea = lines.size * 6 - joinedFaces * 2
        println("Surface $surfaceArea")

        val water = flood(lines)
        var touchingWater = 0
        var tw = mutableListOf<Triple<Int, Int, Int>>()
        for (cube in lines) {
            val neigh = listOf(
                Triple(cube.first + 1, cube.second, cube.third),
                Triple(cube.first - 1, cube.second, cube.third),
                Triple(cube.first, cube.second + 1, cube.third),
                Triple(cube.first, cube.second - 1, cube.third),
                Triple(cube.first, cube.second, cube.third + 1),
                Triple(cube.first, cube.second, cube.third - 1),
            )
            val t = water.intersect(neigh)
            tw.addAll(t)


        }
        println("tw: $tw")
        println("Touching water $touchingWater")
//        val surfaceFaces = exposedFaces.filter { it.value.size > 0 }.keys
//        val allSurfaces = buildSurfaces(surfaceFaces)
//        for (surfaceNum in allSurfaces.keys) {
//            val surface = allSurfaces[surfaceNum]!!
//            val bubbles = buildSurfaces(invertSurface(surface))
//            if (bubbles.size > 1) {
//                println("Bubble ${bubbles}")
//            }
//        }
//        print(buildSurfaces(surfaceFaces))
    }
}
// 2181 low
// 2512 low