package day14

import kotlin.math.sign

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        val world = World2(input)
        return world.addSand()
    }
}

class World2(input: List<String>) {
    private var maxY: Int
    val map = mutableMapOf<Pair<Int,Int>, Char>()

    init {
        input.forEach {
            it.split(" -> ").windowed(2) {
                    (a,b) ->
                val first = a.split(",")
                val second = b.split(",")
                draw(first, second)
            }
        }
        maxY = map.map{it.key.second}.max() + 2
    }

    fun addSand(startX: Int = 500, startY: Int = 0): Int {
        var count = 0
        while (!map.contains(Pair(startX, startY))) {
            addSandAndMove(startX, startY)
            count++
        }

        return count
    }

    private fun addSandAndMove(startX: Int = 500, startY: Int = 0) {
        var blocked = false
        var x = startX
        var y = startY

        while (!blocked) {
            if (y+1 == maxY) {
                blocked = true
            } else if (!map.contains(Pair(x, y+1))) {
                y += 1
            } else if (!map.contains(Pair(x-1, y+1))) {
                x -= 1
                y += 1
            } else if (!map.contains(Pair(x+1, y+1))) {
                x += 1
                y += 1
            } else {
                blocked = true
            }
        }

        map[Pair(x,y)] = 'o'
    }

    private fun draw(first: List<String>, second: List<String>) {
        val firstX = first[0].toInt()
        val firstY = first[1].toInt()
        val secondX = second[0].toInt()
        val secondY = second[1].toInt()

        val xDiff = secondX - firstX
        val yDiff = secondY - firstY

        val xStep = xDiff.sign
        val yStep = yDiff.sign

        var x = firstX
        var y = firstY

        map[Pair(x, y)] = '#'
        do {
            x += xStep
            y += yStep
            map[Pair(x, y)] = '#'
        } while (x != secondX || y != secondY)
    }
}