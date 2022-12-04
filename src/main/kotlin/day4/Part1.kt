package day4

fun main() {
    println(data.count{
        val a = it[0][0].toInt()
        val b = it[0][1].toInt()
        val c = it[1][0].toInt()
        val d = it[1][1].toInt()
        a >= c && b <= d || c >= a && d <= b
    })
}