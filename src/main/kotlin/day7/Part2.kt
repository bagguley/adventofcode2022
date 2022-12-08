package day7

fun main() {
    println(Part2.calc(data))
}

object Part2 {
    lateinit var topDir: Dir2
    fun calc(input: List<String>): Int {
        lateinit var currentDir: Dir2

        input.forEach {
            if (it == "\$ cd /") {
                topDir = Dir2("/")
                currentDir = topDir
            }
            else if (it == "\$ cd ..") {
                currentDir = currentDir.parent!!
            }
            else if (it.startsWith("\$ cd ")) {
                val name = it.substring(5)
                currentDir = currentDir.dirs[name]!!
            }
            else if (it == "\$ ls") {
                // Nothing
            }
            else if (it.startsWith("dir")) {
                currentDir.addDir(it.substring(4))
            }
            else {
                val line = it.split(" ")
                val size = line[0].toInt()
                val name = line[1]
                currentDir.files[name] = size
            }
        }

        val result = mutableListOf<Int>()
        val topDirSize = topDir.size(8381165, result)
        val sizeNeeded = 30000000 - (70000000 - topDirSize)
        result.clear()
        topDir.size(sizeNeeded, result)
        return result.min()
    }
}

class Dir2(val name: String, val parent: Dir2? = null) {
    val dirs = mutableMapOf<String, Dir2>()
    val files = mutableMapOf<String, Int>()
    fun addDir(name: String) {
        dirs[name] = Dir2(name, this)
    }

    fun size(limit: Int, result: MutableList<Int>): Int {
        val size = files.values.sum() + dirs.values.sumOf{it.size(limit, result)}
        if (size >= limit) result.add(size)
        return size
    }
}