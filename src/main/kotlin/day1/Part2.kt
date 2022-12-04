package day1

fun main() {
    println(data.map{it.sum()}.sorted().takeLast(3).sum())
}