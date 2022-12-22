package day15

import kotlin.math.abs

fun main() {
    println(Part1.calc(testData, 10))
    println(Part1.calc(data, 2_000_000))
}

object Part1 {
    fun calc(input: List<String>, row: Int) : Int {
        val sensors = load(input)

        val minX = sensors.minOf { it.minX() }
        val maxX = sensors.maxOf { it.maxX() }

        var count = 0
        for (x in minX..maxX) {

            val inRange = sensors.filter{ it.isInRange(Pair(x, row))}

            if (inRange.isNotEmpty() && inRange.all{it.isNotBeacon(Pair(x, row))}) {
                count++
            }
        }
        return count
    }

    private fun load(input: List<String>): List<Sensor> {
        val result = mutableListOf<Sensor>()

        input.forEach {
            val line = it.split(": closest beacon is at ")
            val sensor = toCoordinates(line[0].substring(10))
            val beacon = toCoordinates(line[1])
            result.add(Sensor(sensor, beacon))
        }

        return result
    }

    private fun toCoordinates(s: String): Pair<Int, Int> {
        val split = s.split(", ")
        return Pair(split[0].substring(2).toInt(), split[1].substring(2).toInt())
    }

}

class Sensor(private val coordinates: Pair<Int, Int>, private val beacon: Pair<Int, Int>) {
    private val distance: Int

    init {
        distance = distance(coordinates, beacon)
    }

    fun isInRange(testCoordinates: Pair<Int, Int>): Boolean {
        return distance(testCoordinates, coordinates) <= distance
    }

    fun isNotBeacon(testCoordinates: Pair<Int, Int>): Boolean {
        return testCoordinates != beacon
    }

    fun minX(): Int {
        return coordinates.first - distance
    }

    fun maxX(): Int {
        return coordinates.first + distance
    }

    private fun distance(from: Pair<Int, Int>, to: Pair<Int, Int>): Int {
        return abs(from.first - to.first) + abs(from.second - to.second)
    }
}
