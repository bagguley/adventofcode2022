package day15

import kotlin.math.abs

fun main() {
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<String>) : Int {
        val sensors = load(input)
        println("edges")
        val edgeSet = mutableSetOf<Pair<Int,Int>>()
        sensors.forEach{ edgeSet.addAll(it.edges()) }
        println("edges done")
        val filtered = edgeSet.filter { edge -> sensors.none { sensor -> sensor.inRange(edge) } }
        filtered.forEach{
            println(it.first * 4_000_000L + it.second)
        }
        return filtered.size
    }

    private fun load(input: List<String>): List<Sensor2> {
        val result = mutableListOf<Sensor2>()

        input.forEach {
            val line = it.split(": closest beacon is at ")
            val sensor = toCoordinates(line[0].substring(10))
            val beacon = toCoordinates(line[1])
            result.add(Sensor2(sensor, beacon))
        }

        return result
    }

    private fun toCoordinates(s: String): Pair<Int, Int> {
        val split = s.split(", ")
        return Pair(split[0].substring(2).toInt(), split[1].substring(2).toInt())
    }

}

class Sensor2(private val coordinates: Pair<Int, Int>, beacon: Pair<Int, Int>) {
    private val distance: Int

    init {
        distance = distance(coordinates, beacon)
    }

    fun edges(): Set<Pair<Int, Int>> {
        val result = mutableSetOf<Pair<Int, Int>>()

        val farLeft = coordinates.first - distance - 1

        var y = 0
        for (x in farLeft..coordinates.first) {
            result.add(Pair(x, coordinates.second + y))
            result.add(Pair(x, coordinates.second - y))

            y++
        }

        val farRight =  coordinates.first + distance + 1
        y = distance + 1
        for (x in coordinates.first..farRight) {
            result.add(Pair(x, coordinates.second + y))
            result.add(Pair(x, coordinates.second - y))

            y--
        }

        return result.filter { inArea(it) }.toSet()
    }

    private fun inArea(point: Pair<Int, Int>): Boolean {
        return point.first in 0..4_000_000 && point.second in 0..4_000_000
    }

    private fun distance(from: Pair<Int, Int>, to: Pair<Int, Int>): Int {
        return abs(from.first - to.first) + abs(from.second - to.second)
    }

    fun inRange(edge: Pair<Int, Int>): Boolean {
        return distance(edge, coordinates) <= distance
    }
}
