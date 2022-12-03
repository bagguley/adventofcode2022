package day3

fun main() {
    val chunked = data.chunked(3)

    println(chunked.sumOf {
        val a = it[0].toCharArray().toSet()
        val b = it[1].toCharArray().toSet()
        val c = it[2].toCharArray().toSet()

        val z = a.intersect(b).intersect(c)
        z.sumOf { Part2.priority(it) }
    })
}

object Part2 {
    fun priority(c: Char):Int {
        return if (c.code > 96) (c.code - 96) else c.code - 64 + 26
    }
}