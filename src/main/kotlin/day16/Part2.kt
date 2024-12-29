package day16

import java.util.*

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
    println(Part2a.calc(testData))
    println(Part2a.calc(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        val valves = load(input)
        val nodes = valves.map { Node(it.name, it.rate, it.leadsTo.map { l -> Link(l, 1) }) }
        val graph = Graph(nodes)

        val toPrune = graph.nodes.filter { it.item == 0 && it.name != "AA" }.map { it.name }
        val prunedGraph = graph.pruning(toPrune)

        var maxScore = 0
        val result = mutableListOf<Path>()
        val queue = PriorityQueue<Path> { t1, t2 ->
            (calcScore(t1, prunedGraph) + maxRemaining(t1, prunedGraph)) - (calcScore(t2, prunedGraph) + maxRemaining(t2, prunedGraph))
        }
        queue.add(Path("AA", "AA", 0, 0, emptyMap()))
        val allPairsCache = mutableMapOf<String, Map<String, Link>>()

        while (queue.isNotEmpty()) {
            val next = queue.remove()

            val allPairs = allPairsCache.getOrPut(next.position) { prunedGraph.allPairs(next.position).associateBy { it.to } }
                .filter { !next.open.containsKey(it.key) }

            val allElephantPairs = allPairsCache.getOrPut(next.elephantPosition) { prunedGraph.allPairs(next.elephantPosition).associateBy { it.to }}
                .filter { !next.open.containsKey(it.key) }

            if (allPairs.isEmpty() && allElephantPairs.isEmpty()) {
                result.add(next)
                maxScore = maxOf(calcScore(next, prunedGraph), maxScore)
            } else {
                val newNext = allPairs.flatMap { myNext ->
                    val elephantNext = allElephantPairs.filter { it.key != myNext.key }
                    if (elephantNext.isEmpty() || next.elephantTime > 24) {
                        listOf(Path(myNext.key, next.elephantPosition, next.time + myNext.value.cost + 1, next.elephantTime + 1, next.open + (myNext.key to next.time + myNext.value.cost + 1)))
                    } else {
                        elephantNext.values.map { elephant ->
                            Path(myNext.key, elephant.to, next.time + myNext.value.cost + 1, next.elephantTime + elephant.cost + 1,
                                next.open + (myNext.key to next.time + myNext.value.cost + 1) + (elephant.to to next.elephantTime + elephant.cost + 1) )
                        }
                    }
                }
                    .filter { it.time <= 26 || it.elephantTime <= 26 }
                    .filter { maxScore <= (calcScore(it, prunedGraph) + maxRemaining(it, prunedGraph)) }

                if (newNext.isEmpty()) {
                    result.add(next)
                    maxScore = maxOf(calcScore(next, prunedGraph), maxScore)
                } else {
                    queue.addAll(newNext)
                }
            }
        }

        return result.maxOf { calcScore(it.open, prunedGraph) }
    }

    private fun load(input: List<String>): List<Valve> {
        return input.map { line ->
            val m = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex().find(line)!!
            with(m.groups) { Valve(get(1)!!.value, get(2)!!.value.toInt(), get(3)!!.value.split(", ")) }
        }
    }

    data class Valve(val name: String, val rate: Int, val leadsTo: List<String>)

    data class Node<T>(val name: String, val item: T, val links: List<Link>)

    data class Link(val to: String, val cost: Int)

    data class Path(val position: String, val elephantPosition: String, val time: Int, val elephantTime: Int, val open: Map<String, Int>)

    class Graph<T>(val nodes: List<Node<T>>) {
        private val nodeMap: Map<String, Node<T>> = nodes.associateBy { it.name }

        fun names(): Set<String> = nodeMap.keys

        fun get(name: String): Node<T> = nodeMap[name]!!

        fun pruning(toPrune: List<String>): Graph<T> {
            return toPrune.fold(this) { acc, item -> acc.pruning(item) }
        }

        private fun pruning(toPrune: String): Graph<T> {
            val newLinks = nodeMap[toPrune]!!.links

            val newNodes = nodeMap.values.filter { it.name != toPrune}.map { node ->
                if (node.links.any { l -> l.to == toPrune }) {
                    val prunedLink = node.links.first { it.to == toPrune }
                    val replacedLinks = node.links.filter { l -> l.to != toPrune } +
                            newLinks.filter { n -> n.to != node.name}
                                .map { n -> Link(n.to, n.cost + prunedLink.cost) }
                    Node(node.name, node.item, replacedLinks)
                } else node
            }

            return Graph(newNodes)
        }

        fun allPairs(from: String): List<Link> {
            val costs = mutableMapOf<String, Link>()
            val queue = mutableListOf(Link(from, 0))

            while (queue.isNotEmpty()) {
                val head = queue.removeFirst()
                val node = nodeMap[head.to]!!
                val nextNodes = node.links.filter { l -> !costs.containsKey(l.to) }
                    .map { l -> Link(l.to, l.cost + head.cost) }

                costs.putAll(nextNodes.associateBy { it.to })
                queue.addAll(nextNodes)
            }
            return costs.values.toList()
        }
    }

    private fun calcScore(open: Map<String, Int>, graph: Graph<Int>): Int = open.entries.sumOf { (26 - it.value) * graph.get(it.key).item }

    private fun calcScore(path: Path, graph: Graph<Int>): Int = calcScore(path.open, graph)

    private fun maxRemaining(path: Path, graph: Graph<Int>): Int = (graph.names() - path.open.keys).sumOf { graph.get(it).item * (26 - path.time) }
}

object Part2a {
    fun calc(input: List<String>): Int {
        val valves = load(input).associateBy { it.name }
        val score = score("AA", 26, 2, emptySet(), valves, mutableMapOf())
        return score.first
    }

    private fun score(position: String, time: Int, people: Int, open: Set<String>, valves: Map<String, Valve>, cache: MutableMap<Pair<Triple<String, Int, Set<String>>, Int>, Pair<Int, Set<String>>>): Pair<Int, Set<String>> {
        if (time <= 1 && people == 1) return 0 to open
        if (time <= 1 && people > 1) {
            return score("AA", 26, people - 1, open, valves, cache)
        }

        val key = Triple(position, time, open) to people
        if (cache.containsKey(key)) return cache[key]!!

        val valve = valves[position]!!

        val score: Pair<Int, Set<String>> = if (valve.rate > 0 && valve.name !in open) {
            val opened = valve.leadsTo.map { score(it, time - 2, people, open + valve.name, valves, cache) }.maxBy { it.first }.let { it.first + valve.rate * (time - 1) to it.second }
            val notOpened = valve.leadsTo.map { score(it, time - 1, people, open, valves, cache) }.maxBy { it.first }
            listOf(opened, notOpened).maxBy { it.first }
        } else {
            valve.leadsTo.map { score(it, time - 1, people, open, valves, cache) }.maxBy { it.first }
        }

        cache[key] = score
        return score
    }

    private fun load(input: List<String>): List<Valve> {
        return input.map { line ->
            val m = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex().find(line)!!
            with(m.groups) { Valve(get(1)!!.value, get(2)!!.value.toInt(), get(3)!!.value.split(", ")) }
        }
    }

    data class Valve(val name: String, val rate: Int, val leadsTo: List<String>)
}