package day16

import java.util.PriorityQueue

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        val valves = load(input).associateBy { it.name }

        var maxScore = 0
        val result = mutableListOf<Path>()
        val queue = PriorityQueue<Path> { t1, t2 ->
            (calcScore(t2, valves) + maxRemaining(t2, valves)) - (calcScore(t1, valves) + maxRemaining(t1, valves))
        }
        queue.add(Path("AA", 0, emptyMap()))
        val allPairsCache = mutableMapOf<String, Map<String, Int>>()

        while (queue.isNotEmpty()) {
            val next = queue.remove()

            if (next.time == 29) {
                result.add(next)
                maxScore = maxOf(calcScore(next, valves), maxScore)
                continue
            }

            val allPairs = allPairsCache.getOrPut(next.position) { allPairs(next.position, valves) }
                .filter { !next.open.containsKey(it.key) }

            if (allPairs.isEmpty()) {
                result.add(next)
                maxScore = maxOf(calcScore(next, valves), maxScore)
            } else {
                val newNext = allPairs.map { Path(it.key, next.time + it.value + 1, next.open + (it.key to next.time + it.value + 1)) }
                    .filter { maxScore <= (calcScore(it, valves) + maxRemaining(it, valves)) }
                queue.addAll(newNext)
            }
        }

        return result.maxOf { calcScore(it.open, valves) }
    }

    private fun load(input: List<String>): List<Valve> {
        return input.map { line ->
            val m = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex().find(line)!!
            with(m.groups) { Valve(get(1)!!.value, get(2)!!.value.toInt(), get(3)!!.value.split(", ")) }
        }
    }

    private fun allPairs(startAt: String, valves: Map<String, Valve>): Map<String, Int> {
        val costs = mutableMapOf<String, Int>()
        val queue = mutableListOf(startAt to 0)

        while (queue.isNotEmpty()) {
            val head = queue.removeFirst()
            val valve = valves[head.first]!!
            val children = valve.leadsTo
            val a = children.filter { !costs.containsKey(it) }
                .mapNotNull { valves[it] }
                .onEach { costs[it.name] = head.second + 1 }
                .map { it.name }
            queue.addAll(a.map { it to head.second + 1})
        }
        return costs.filter { valves[it.key]!!.rate > 0 }
    }

    private fun calcScore(open: Map<String, Int>, valves: Map<String, Valve>): Int = open.entries.sumOf { (30 - it.value) * valves[it.key]!!.rate }

    private fun calcScore(path: Path, valves: Map<String, Valve>): Int = calcScore(path.open, valves)

    private fun maxRemaining(path: Path, valves: Map<String, Valve>): Int {
        return (valves.keys - path.open.keys).sumOf { valves[it]!!.rate * (30 - path.time) }
    }

    data class Valve(val name: String, val rate: Int, val leadsTo: List<String>)

    data class Path(val position: String, val time: Int, val open: Map<String, Int>)
}