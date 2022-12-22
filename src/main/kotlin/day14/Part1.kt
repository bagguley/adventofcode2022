package day14

import kotlin.math.sign

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        val world = World(input)
        var count = 0

        while (!world.addSand()) {
            count ++
        }

        return count
    }
}

class World(input: List<String>) {
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
    }

    fun addSand(startX: Int = 500, startY: Int = 0): Boolean {
        val maxY = map.map{it.key.second}.max()
        var blocked = false
        var abyss = false
        var x = startX
        var y = startY

        while (!blocked && !abyss) {
            if (!map.contains(Pair(x, y+1))) {
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
            if (y > maxY) abyss = true
        }

        if (blocked) {
            map[Pair(x,y)] = 'o'
        }
        return abyss
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