package day7

fun main() {
    println(Part1.calc(data))
}

object Part1 {
    lateinit var topDir: Dir
    fun calc(input: List<String>): Int {
        lateinit var currentDir: Dir

        input.forEach {
            if (it == "\$ cd /") {
                topDir = Dir("/")
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
        topDir.size(100000, result)
        return result.sum()
    }
}

class Dir(val name: String, val parent: Dir? = null) {
    val dirs = mutableMapOf<String, Dir>()
    val files = mutableMapOf<String, Int>()
    fun addDir(name: String) {
        dirs[name] = Dir(name, this)
    }

    fun size(limit: Int, result: MutableList<Int>): Int {
        val size = files.values.sum() + dirs.values.sumOf{it.size(limit, result)}
        if (size <= limit) result.add(size)
        return size
    }
}