package day16

import helpers.ReadFile
import java.lang.Integer.min

class Day16 {

    class Node {
        val name: String
        var isOn = false
        val rate: Int
        val connections: MutableList<Node> = mutableListOf()
        val shortestDistanceToNode: MutableMap<String, Int> = mutableMapOf()
        val conStrings: List<String>
        var best: MutableMap<Pair<Int, Set<String>>,Pair<List<String>,Int>> = mutableMapOf()
        constructor(name: String, rate: Int, conStrings: List<String>) {
            this.name = name
            this.rate = rate
            this.conStrings = conStrings
        }

        override fun toString(): String {
            return "$name ($rate) -> ${connections.map { it.name }}"
        }
    }
    val lines = ReadFile.named("src/day16/input.txt")
    val tlines = listOf(
        "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB",
        "Valve BB has flow rate=13; tunnels lead to valves CC, AA",
        "Valve CC has flow rate=2; tunnels lead to valves DD, BB",
        "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE",
        "Valve EE has flow rate=3; tunnels lead to valves FF, DD",
        "Valve FF has flow rate=0; tunnels lead to valves EE, GG",
        "Valve GG has flow rate=0; tunnels lead to valves FF, HH",
        "Valve HH has flow rate=22; tunnel leads to valve GG",
        "Valve II has flow rate=0; tunnels lead to valves AA, JJ",
        "Valve JJ has flow rate=21; tunnel leads to valve II",
    )

    var validNodesCount = 0
    var validNodeNames = setOf<String>()
    val nodes = mutableMapOf<String, Node>()
    fun findBestDistance(baseNode: Node, node: Node, depth: Int, visited: Set<String>) {
        val newVisited = visited + node.name
        if (baseNode.conStrings.contains(node.name) && node.rate > 0) {
            val currentBest = baseNode.shortestDistanceToNode[node.name]
            if (currentBest == null || currentBest > depth) {
                baseNode.shortestDistanceToNode[node.name] = depth
            }
        }
        // If all the potential connections have already got a lower distance
        var end = true
        for (con in node.connections) {
            if (baseNode.shortestDistanceToNode[con.name] == null || baseNode.shortestDistanceToNode[con.name]!! > depth) {
                end = false
                break
            }
        }
        if (end) {
            return
        }

        for (con in node.connections) {
            if (newVisited.contains(con.name)) {
                continue
            }


            if (con.rate > 0) {
                val currentBest = baseNode.shortestDistanceToNode[con.name]
                if (currentBest == null || currentBest > depth) {
                    baseNode.shortestDistanceToNode[con.name] = depth + 1
                }
            }
            findBestDistance(baseNode, con, depth + 1, newVisited)
        }
    }

    fun fp(remainingTime: Int, node: Node, currentPressure: Int, totalPressure: Int, path: List<String>) : Pair<Int, List<String>> {

        var options = validNodeNames.toMutableSet()
        options.removeAll(path)
        if (options.isEmpty()) {
            return Pair(totalPressure + remainingTime * currentPressure, path)
        }
        var results = mutableListOf<Pair<Int, List<String>>>()
        for (option in options) {
            val distance = node.shortestDistanceToNode[option]!!
            val target = nodes[option]!!
            if (remainingTime - distance >= 0) {
                results.add(fp(
                    remainingTime - distance,
                    target,
                    currentPressure + target.rate,
                    totalPressure + currentPressure * distance,
                    path + option
                ))
            } else {
                 results.add(Pair(totalPressure + currentPressure * remainingTime, path))
            }
        }
        return results.maxBy { it.first }!!
    }

    fun singleHelper(options: Set<String>, active: Node, other: Node, otherActiveIn: Int, remainingTime: Int, totalPressure: Int, pathz: Pair<List<String>, List<String>>, isA: Boolean): List<Pair<Int, Pair<List<String>,List<String>>>> {
        var results = mutableListOf<Pair<Int, Pair<List<String>,List<String>>>>()
        for (option in options) {
            val distance = active.shortestDistanceToNode[option]!!
            var nextJump = distance
            if (otherActiveIn < distance) {
                nextJump = otherActiveIn
            }

            val target = nodes[option]!!
            if (remainingTime - distance >= 0) {
                // Add all of the actives travel distance to total now
                results.add(fp2(
                    remainingTime - nextJump,
                    target,
                    other,
                    distance - nextJump,
                    otherActiveIn - nextJump,
                    totalPressure + (remainingTime - distance) * target.rate,
                    if (isA) Pair(pathz.first + option, pathz.second) else Pair(pathz.first, pathz.second + option)
                ))
            }
        }
        return results
    }
    fun fp2(remainingTime: Int, a: Node, b: Node, aActiveIn: Int, bActiveIn: Int, totalPressure: Int, pathz: Pair<List<String>, List<String>>) : Pair<Int, Pair<List<String>, List<String>>> {

        var options = validNodeNames.toMutableSet()
        options.removeAll((pathz.first + pathz.second).toSet())
        if (options.isEmpty() || remainingTime <= 0) {
            return Pair(totalPressure, pathz)
        }
        // If active, then do the below
        // The next step is the lowest of other active and distance
        var results = mutableListOf<Pair<Int, Pair<List<String>,List<String>>>>()
        if (aActiveIn > 0 && bActiveIn > 0) {
            println("Ooops")
        } else if (aActiveIn == 0 && bActiveIn > 0) {
            results.addAll(singleHelper(options, a, b, bActiveIn, remainingTime, totalPressure, pathz, true))
        } else if (bActiveIn == 0 && aActiveIn > 0) {
            results.addAll(singleHelper(options, b, a, aActiveIn, remainingTime, totalPressure, pathz, false))
        } else if (aActiveIn == 0 && bActiveIn == 0) {
            for (optionA in options) {
                for (optionB in options) {
                    if (optionA == optionB) {
                        continue
                    }
                    val distanceA = a.shortestDistanceToNode[optionA]!!
                    val distanceB = b.shortestDistanceToNode[optionB]!!
                    val nextJump = min(distanceA, distanceB)
                    val targetA = nodes[optionA]!!
                    val targetB = nodes[optionB]!!

                    if (remainingTime - nextJump >= 0) {
                        // ADD ALL ADDITIONAL PRESSURE HERE
                        var newTotalPressure = totalPressure
                        if (remainingTime - distanceA >= 0) {
                            newTotalPressure += (remainingTime - distanceA) * targetA.rate
                        }
                        if (remainingTime - distanceB >= 0) {
                            newTotalPressure += (remainingTime - distanceB) * targetB.rate
                        }

                        results.add(fp2(
                            remainingTime - nextJump,
                            targetA,
                            targetB,
                            distanceA - nextJump,
                            distanceB - nextJump,
                            newTotalPressure,
                            Pair(pathz.first + optionA, pathz.second + optionB)
                        ))
                    }


                }
            }
        } else {
            println("Ooops")
        }
        if (results.isEmpty()) {
            return Pair(totalPressure, pathz)
        }
        return results.maxBy { it.first }!!
    }


    fun result1() {

        for (line in lines) {
            val name = line.substring(6, 8)
            val bits = line.drop(23).split(";")
            val rate = bits[0].toInt()
            val chars = if (bits[1].contains("tunnels")) 24 else 23
            val constring = bits[1].drop(chars).split(", ")
            val n = Node(name, rate, constring)
            nodes[name] = n
        }
        for (node in nodes.values) {
            for (con in node.conStrings) {
                node.connections.add(nodes[con]!!)
            }
        }

        validNodeNames = nodes.values.filter { it.rate > 0 }.map { it.name }.toSet()
        validNodesCount = validNodeNames.count()

        for (node in nodes.values) {
            if (node.name == "AA" || node.rate > 0) {
                for (con in node.connections) {
                    findBestDistance(node, con, 1, setOf(node.name))
                }
            }
        }
        nodes.values.filter { it.rate > 0 || it.name == "AA" }.forEach() {
            for (k in it.shortestDistanceToNode.keys) {
                // Travel time + turn on time
                it.shortestDistanceToNode[k] = it.shortestDistanceToNode[k]!! + 1
            }
        }

        println(fp2(26, nodes["AA"]!!, nodes["AA"]!!, 0,0,0, Pair(listOf("AA"),listOf("AA"))))


        // A, D, Do, C, B, Bo, A, I, J, Jo, I, A, D, E, F, G, H, Ho, G, F, E, Eo, D, C, Co
        //[A, D, Do, C, B, Bo, A, I, J, Jo, I, A, D, E, Eo, F, G, H, Ho, G, F, E, F]
        //[A, D, Do, C, Co, D, C, D, C, B, Bo, A, I, J, Jo, I, A, D, E, Eo, F, Fo, G, Go, H, Ho, G, F, E, D, C]

//        val x = findPath2(12, nodes["AA"]!!, nodes["AA"]!!, 0, 0, Pair(listOf("AA"),listOf("AA")))
////        println(x.first.map { it.drop(1) })
//        println("tot: $validNodesCount")
//        println(x)

    }
    // Pos A/B time remaining and open values
    var cache: MutableMap<Pair<Set<String>, Pair<Int, Set<String>>>, Pair<Pair<List<String>, List<String>>,Int>> = mutableMapOf()
    var cacheHit = 0

    fun findPath2(remainingTime: Int, a: Node, b: Node, currentPressure: Int, totalPressure: Int, path2: Pair<List<String>,List<String>>): Pair<Pair<List<String>, List<String>>,Int> {
        // Enter function reduce to by 1 and turn on
        if (remainingTime <= 0) {
            return Pair(path2, totalPressure)
        }

        val onValves = (path2.first + path2.second).filter() {
            it.endsWith("o")
        }.toSet()

        if (onValves.size == validNodesCount) {
            return Pair(path2, totalPressure + currentPressure * remainingTime)
        }

        val key = Pair(setOf(a.name, b.name), Pair(remainingTime, onValves))

        if (cache[key] != null) {
            cacheHit++
            if (cacheHit % 100000 == 0) {
                println("Cache hit: $cacheHit")
            }
            val extension = cache[key]!!
            return Pair(Pair(path2.first + extension.first.first, path2.second + extension.first.second), totalPressure + extension.second)
        }

        var paths = mutableListOf<Pair<Pair<List<String>, List<String>>,Int>>()

        if (a.name != b.name) {

            var doA = false;
            var doB = false;
            if (a.rate > 0 && !onValves.contains("${a.name}o") && remainingTime >= 1) {
                doA = true
                val newPressure = currentPressure + a.rate
                paths.addAll(b.connections.map {
                    findPath2(remainingTime - 1, a, it, newPressure, totalPressure + currentPressure, Pair(path2.first + "${a.name}o", path2.second + it.name))
                }.toMutableList())
            }

            if (b.rate > 0 && !onValves.contains("${b.name}o") && remainingTime >= 1) {
                doB = true
                val newPressure = currentPressure + b.rate
                paths.addAll(a.connections.map {
                    findPath2(remainingTime - 1, it, b, newPressure, totalPressure + currentPressure, Pair(path2.first + it.name, path2.second + "${b.name}o"))
                }.toMutableList())
            }
            if (doA && doB) {
                val doublePressure = currentPressure + a.rate + b.rate
                paths.add(
                    findPath2(
                        remainingTime - 1,
                        a,
                        b,
                        doublePressure,
                        totalPressure + currentPressure,
                        Pair(path2.first + "${a.name}o", path2.second + "${b.name}o")
                    )
                )
            }
            paths.addAll(a.connections.flatMap {newA ->
                b.connections.map {newB ->
                    findPath2(remainingTime - 1, newA, newB, currentPressure, totalPressure + currentPressure, Pair(path2.first + newA.name, path2.second + newB.name))
                }
            }.toMutableList())
        } else {
            // Only 1 can switch it on, doesn't matter which
            if (a.rate > 0 && !onValves.contains("${a.name}o") && remainingTime >= 1) {
                val newPressure = currentPressure + a.rate
                paths.addAll(b.connections.map {
                    findPath2(remainingTime - 1, a, it, newPressure, totalPressure + currentPressure, Pair(path2.first + "${a.name}o", path2.second + it.name))
                }.toMutableList())
            }
            // Either move to any connection, or, if possible, turn on the valve

            paths.addAll(a.connections.flatMap {newA ->
                b.connections.map {newB ->
                    findPath2(remainingTime - 1, newA, newB, currentPressure, totalPressure + currentPressure, Pair(path2.first + newA.name, path2.second + newB.name))
                }
            }.toMutableList())
        }

        val best =  paths.maxBy { it.second }!!

        if (cache[key] == null) {
            // Just the end bit
            cache[key] = Pair(Pair(best.first.first.drop(path2.first.size), best.first.second.drop(path2.second.size)), best.second - totalPressure)
        }
        return best
    }

    fun findPath(remainingTime: Int, currentNode: Node, currentPressure: Int, totalPressure: Int, path: List<String>): Pair<List<String>,Int> {
        // Enter function reduce to by 1 and turn on
        if (remainingTime <= 0) {
            return Pair(path, totalPressure)
        }
        val onValves = path.filter() {
            it.endsWith("o")
        }.toSet()
        val key = Pair(remainingTime, onValves)

        if (currentNode.best[key] != null) {
            val extension = currentNode.best[key]!!
            return Pair(path + extension.first, totalPressure + extension.second)
        }

        var paths = mutableListOf<Pair<List<String>,Int>>()
        // Either move to any connection, or, if possible, turn on the valve
        if (!onValves.contains("${currentNode.name}o") && remainingTime >= 1 && currentNode.rate > 0) {
            val newPressure = currentPressure + currentNode.rate
            paths.add(findPath(remainingTime - 1, currentNode, newPressure, totalPressure + currentPressure, path + "${currentNode.name}o"))
        }
        paths.addAll(currentNode.connections.map {
            findPath(remainingTime - 1, it, currentPressure, totalPressure + currentPressure, path + it.name)
        }.toMutableList())


        val best =  paths.maxBy { it.second }!!
        val existingBest = currentNode.best[key]
        // Can't be null here anyway
        if (existingBest == null || best.second > existingBest.second ) {
            // Just the end bit
            currentNode.best[key] = Pair(best.first.drop(path.size), best.second - totalPressure)
        }
        return best
    }

}