package day9

import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<List<String>>): Int {

        val ropes = Ropes()

        for (i in input) {
            ropes.moveHead(i[0], i[1].toInt())
        }

        return ropes.visited.count()
    }
}

class Ropes {
    val visited = mutableSetOf(Pair(0, 0))
    private var head = Pair(0, 0)
    private var tail = Pair(0, 0)

    fun moveHead(dir: String, dist: Int) {
        val movement = when (dir) {
            "L" -> Pair(-1, 0)
            "U" -> Pair(0, 1)
            "R" -> Pair(1, 0)
            "D" -> Pair(0, -1)
            else -> throw RuntimeException("Oh noes!")
        }


        for (i in 1..dist) {
            head = Pair(head.first + movement.first, head.second + movement.second)

            moveTail(movement)
        }
    }

    private fun moveTail(movement: Pair<Int, Int>) {
        if (abs(head.first - tail.first) > 1 || abs(head.second - tail.second) > 1) {
            if (head.first == tail.first || head.second == tail.second) {
                tail = Pair(tail.first + movement.first, tail.second + movement.second)
            } else {
                val dif = Pair(sign((head.first - tail.first).toDouble()).toInt(), sign((head.second - tail.second).toDouble()).toInt())
                tail = Pair(tail.first + dif.first, tail.second + dif.second)
            }
        }
        visited.add(tail)
    }
}