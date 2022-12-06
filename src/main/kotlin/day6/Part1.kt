package day6

fun main() {
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: String): Int {
        return data.windowed(4).takeWhile{it.toSet().size!=4}.count()+4
    }
}
