package day9

import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(testData2))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<List<String>>): Int {

        val ropes = Ropes2(10)

        for (i in input) {
            ropes.moveHead(i[0], i[1].toInt())
        }

        return ropes.last().visited.count()
    }
}

class Ropes2(num: Int) {
    private var head: Knot

    init {
        var knot: Knot? = null
        for (i in 1..num) {
            knot = Knot(knot)
        }
        head = knot!!
    }

    fun moveHead(dir: String, dist: Int) {
        head.move(dir, dist)
    }

    fun last(): Knot {
        return head.last()
    }
}

class Knot(val next: Knot? = null) {
    val visited = mutableSetOf(Pair(0, 0))
    private var coords = Pair(0,0)

    fun move(dir: String, dist: Int) {
        val movement = when (dir) {
            "L" -> Pair(-1, 0)
            "U" -> Pair(0, 1)
            "R" -> Pair(1, 0)
            "D" -> Pair(0, -1)
            else -> throw RuntimeException("Oh noes!")
        }

        for (i in 1..dist) {
            coords = Pair(x() + movement.first, y() + movement.second)

            next?.move(this)
        }
    }

    private fun move(head: Knot) {
        if (abs(head.x() - x()) > 1 || abs(head.y() - y()) > 1) {
            if (head.x() == x() || head.y() == y()) {
                val dif = Pair(sign((head.x() - x()).toDouble()).toInt(), sign((head.y() - y()).toDouble()).toInt())
                coords = Pair(x() + dif.first, y() + dif.second)
            } else {
                val dif = Pair(sign((head.x() - x()).toDouble()).toInt(), sign((head.y() - y()).toDouble()).toInt())
                coords = Pair(x() + dif.first, y() + dif.second)
            }
        }

        visited.add(coords)

        next?.move(this)
    }

    fun x() = coords.first
    fun y() = coords.second

    fun last(): Knot {
        return next?.last() ?: this
    }
}