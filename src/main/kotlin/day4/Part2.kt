package day4

fun main() {
    println(data.map{
        val a = it[0][0].toInt()
        val b = it[0][1].toInt()
        val c = it[1][0].toInt()
        val d = it[1][1].toInt()
        (a in c..d) || (b in c..d) || (c in a..b) || (d in a..b)
    }.count { it })
}