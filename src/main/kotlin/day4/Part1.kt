package day4

fun main() {
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<List<List<Int>>>): Int {
        return input.count{it[0][0]>=it[1][0]&&it[0][1]<=it[1][1]||it[1][0]>=it[0][0]&&it[1][1]<=it[0][1]}
    }
}