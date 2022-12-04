package day3

fun main() {
    println(Part1.calc(data))
    println(Part1.calcOneLine(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        return input.map{it.intersect()}.sumOf{priority(it)}
    }

    private fun priority(c: Char):Int {
        return if (c.code > 96) (c.code - 96) else c.code - 64 + 26
    }

    private fun String.intersect(): Char {
        return this.substring(0,length/2).toSet().intersect(this.substring(length/2).toSet()).first()
    }

    fun calcOneLine(input: List<String>): Int {
        return input.map{it.substring(0,it.length/2).toSet().intersect(it.substring(it.length/2).toSet()).first()}.sumOf{it.code.and(31)+it.code.shr(5).and(1).xor(1)*26}
    }
}

