package day1

fun main() {
    println(data.map{it.sumOf{it.toLong()}}.sorted().takeLast(3).sum())
}