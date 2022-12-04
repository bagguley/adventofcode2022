package day3

fun main() {
    println(Part2.calc(data))
    println(Part2.calcOneLine(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        return input.map{it.toSet()}.chunked(3).sumOf{it[0].intersect(it[1]).intersect(it[2]).first().let{priority(it)}}
    }

    private fun priority(c: Char):Int {
        return if (c.code > 96) (c.code - 96) else c.code - 64 + 26
    }

    fun calcOneLine(input: List<String>): Int {
        return input.map{it.toSet()}.chunked(3).sumOf{it[0].intersect(it[1]).intersect(it[2]).first().code.let{it.and(31)+it.shr(5).and(1).xor(1)*26}}
    }
}