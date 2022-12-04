package day3

fun main() {
    println(data.map{it.intersect()}.sumOf{it.sumOf{Part1.priority(it)}})
}

object Part1 {
    fun priority(c: Char):Int {
        return if (c.code > 96) (c.code - 96) else c.code - 64 + 26
    }
}

fun String.intersect(): Set<Char> {
    return this.substring(0,length/2).toSet().intersect(this.substring(length/2).toSet())
}
