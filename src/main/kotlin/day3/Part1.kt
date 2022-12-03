package day3

fun main() {
    println(data.sumOf {
        val l = it.length
        val left = it.substring(0, l / 2).toCharArray()
        val right = it.substring(l / 2).toCharArray()
        val c = left.intersect(right.toSet())
        c.sumOf { Part1.priority(it) }
    })
}

object Part1 {
    fun priority(c: Char):Int {
        return if (c.code > 96) (c.code - 96) else c.code - 64 + 26
    }
}