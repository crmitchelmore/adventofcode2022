package day7

import helpers.ReadFile

class File {
    var size: Int = 0
    var parent: File? = null
    var contents: MutableMap<String, File>? = null
    constructor(size: Int, parent: File?) {
        if (size == 0) {
            contents = mutableMapOf()
        }
        this.parent = parent
        this.size = size
    }
}
class Day7 {
    val lines = ReadFile.named("src/day7/input.txt").drop(1)
//    val lines = listOf(
//    "$ ls",
//    "dir a",
//    "14848514 b.txt",
//    "8504156 c.dat",
//    "dir d",
//    "$ cd a",
//    "$ ls",
//    "dir e",
//    "29116 f",
//    "2557 g",
//    "62596 h.lst",
//    "$ cd e",
//    "$ ls",
//    "584 i",
//    "$ cd ..",
//    "$ cd ..",
//    "$ cd d",
//    "$ ls",
//    "4060174 j",
//    "8033020 d.log",
//    "5626152 d.ext",
//    "7214296 k"
//    )

    var root = File(size = 0, parent = null)
    var currentDir = root

    fun result1(): String {
        for (line in lines) {
            val chunks = line.split(" ")
            println(line)
            if (line.startsWith("$ ")) {

                val cmd = chunks[1]
                if (cmd == "cd") {
                    val dir = chunks[2]
                    if (dir == "..") {
                        currentDir = currentDir.parent!!
                    } else if (currentDir.contents!![dir] == null) {
                        throw   Exception("no such directory")
                    } else {
                        currentDir = currentDir.contents!![dir]!!
                    }
                } else if (cmd == "ls") {
                    //Do nothing
                } else {
                    throw Exception("Unknown command $cmd")
                }
            } else {
                val size = if (chunks[0] == "dir") 0 else chunks[0].toInt()
                currentDir.contents!![chunks[1]] = File(size, parent = currentDir)
            }
        }
        val totalSpace = 70000000
        val usedSpace = calculateDirSize(root)
        val freeSpace = totalSpace - usedSpace
        val requiredSpace = 30000000
        val whichDelete = allDirs(root, listOf()).sortedBy { it.size }.first { it.size + freeSpace >= requiredSpace }
        return whichDelete.size.toString()
//        val dirs = dirsWithAtMost100k(root, listOf())
//        println(dirs)
//        return dirs.sumOf { it.size }.toString()
    }

    fun allDirs(f: File, dirs: List<File>): List<File> {
        var moreDirs = dirs.toMutableList()
        f.contents!!.values.forEach() {
            if (it.contents != null) {
                moreDirs.add(it)
                moreDirs.addAll(allDirs(it, dirs))
            }
        }
        return moreDirs
    }
    fun dirsWithAtMost100k(f: File, dirs: List<File>): List<File> {
        var moreDirs = dirs.toMutableList()
        f.contents!!.values.forEach() {
            if (it.contents != null) {
                if (it.size <= 100000) {
                    moreDirs.add(it)
                }
                moreDirs.addAll(dirsWithAtMost100k(it, dirs))
            }
        }
        return moreDirs
    }

    fun calculateDirSize(f: File): Int {
        if (f.size == 0) {
            f.size = f.contents!!.values.fold(0, { acc, file -> acc + calculateDirSize(file) })
            return f.size
        }
        return f.size
    }
}