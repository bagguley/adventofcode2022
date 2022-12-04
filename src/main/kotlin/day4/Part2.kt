package day4

fun main() {
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<List<List<Int>>>): Int {
        return input.count{(it[0][0]in it[1][0]..it[1][1])||(it[0][1]in it[1][0]..it[1][1])||(it[1][0]in it[0][0]..it[0][1])||(it[1][1]in it[0][0]..it[0][1])}
    }
}