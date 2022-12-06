package day6

fun main() {
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: String): Int {
        return data.windowed(14).takeWhile{it.toSet().size!=14}.count()+14
    }
}
