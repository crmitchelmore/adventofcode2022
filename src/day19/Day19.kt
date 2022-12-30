package day19

import helpers.ReadFile

class Day19 {
    class Blueprint {
        val number: Int
        val ore: Int
        val clay: Int
        val obsidian: Pair<Int, Int>
        val geode: Pair<Int, Int>
        val maxOre: Int

        constructor(number: Int, ore: Int, clay: Int, obsidian: Pair<Int, Int>, geode: Pair<Int, Int>) {
            this.number = number
            this.ore = ore
            this.clay = clay
            this.obsidian = obsidian
            this.geode = geode
            this.maxOre = listOf(ore, clay, obsidian.first, geode.first).max()!!
        }
    }
    //Blueprint 11: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 17 clay. Each geode robot costs 3 ore and 11 obsidian.
    val rlines = ReadFile.named("src/day19/input.txt")
    val tlines = listOf(
//        "1,4,4,4,14,3,16"
        "1,4,2,3,14,2,7",
        "2,2,3,3,8,3,12"
    )

    val lines = rlines.map {
        val nums = it.split(",").map { it.toInt() }
        Blueprint(nums[0], nums[1], nums[2], Pair(nums[3], nums[4]), Pair(nums[5], nums[6]))
    }

    val case = listOf(
        "nothing", "nothing", "clay", "nothing", "clay", "nothing", "clay", "nothing", "nothing", "nothing", "obsidian", "clay", "nothing", "nothing", "obsidian", "nothing", "nothing", "geode", "nothing", "nothing", "geode", "nothing"
    )
    var currentBest = 0
    fun stuff(blueprint: Blueprint, stepsRemaining: Int, ore: Pair<Int, Int>, clay: Pair<Int, Int>, obsidian: Pair<Int, Int>, geode: Pair<Int, Int>, path: List<String>) : Int {
        if (stepsRemaining == 0) {
            if (geode.second > currentBest) {
                println("New best: ${geode.second} with path ${path}")
                currentBest = geode.second
            }
            return geode.second
        }
        if (stepsRemaining < 10) {
            val maxPotential = geode.second + geode.first * stepsRemaining + ((stepsRemaining-1) * (stepsRemaining)/2)
            if (maxPotential < currentBest) {
                return 0
            }
        }

        var results = mutableListOf<Int>()
//        if (path == case) {
//            println(path)
//        }
        if (ore.second >= blueprint.ore && ore.first <= blueprint.maxOre) {
            results.add(stuff(blueprint, stepsRemaining - 1, Pair(ore.first + 1, ore.second - blueprint.ore + ore.first), Pair(clay.first, clay.second + clay.first), Pair(obsidian.first, obsidian.second + obsidian.first), Pair(geode.first, geode.second + geode.first), path + "ore"))
        }
        if (ore.second >= blueprint.clay && clay.first <= blueprint.obsidian.second) {
            results.add(stuff(blueprint, stepsRemaining - 1, Pair(ore.first, ore.second + ore.first - blueprint.clay), Pair(clay.first + 1, clay.second + clay.first), Pair(obsidian.first, obsidian.second + obsidian.first), Pair(geode.first, geode.second + geode.first), path + "clay"))
        }
        if (ore.second >= blueprint.obsidian.first && clay.second >= blueprint.obsidian.second && obsidian.first <= blueprint.geode.second) {
            results.add(stuff(blueprint, stepsRemaining - 1, Pair(ore.first, ore.second + ore.first - blueprint.obsidian.first), Pair(clay.first, clay.second + clay.first - blueprint.obsidian.second), Pair(obsidian.first + 1, obsidian.second + obsidian.first), Pair(geode.first, geode.second + geode.first), path + "obsidian"))
        }
        if (ore.second >= blueprint.geode.first && obsidian.second >= blueprint.geode.second) {
            results.add(stuff(blueprint, stepsRemaining - 1, Pair(ore.first, ore.second + ore.first - blueprint.geode.first), Pair(clay.first, clay.second + clay.first), Pair(obsidian.first, obsidian.second + obsidian.first - blueprint.geode.second), Pair(geode.first + 1, geode.second + geode.first), path + "geode"))
        }
        results.add(stuff(blueprint, stepsRemaining - 1, Pair(ore.first, ore.second + ore.first), Pair(clay.first, clay.second + clay.first), Pair(obsidian.first, obsidian.second + obsidian.first), Pair(geode.first, geode.second + geode.first),path + "nothing"))
        return results.max()!!

    }
    fun result1() {
//        val totals = lines.map { blueprint ->
//            currentBest = 0
//            val geodes = stuff(blueprint, 24, Pair(1, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), listOf())
//            println("Blueprint ${blueprint.number}: $geodes")
//            blueprint.number * geodes
//        }
//
//        println("High: ${totals.sum()}")
        val totals = lines.take(3).map { blueprint ->
            currentBest = 0
            val geodes = stuff(blueprint, 32, Pair(1, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), listOf())
            println("Blueprint ${blueprint.number}: $geodes")
            geodes
        }

        println("High: ${totals[0] * totals[1] * totals[2]}")
    }
}

