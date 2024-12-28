package day16

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        val valves = load(input)
        val nodes = valves.map { Node(it.name, it.rate, it.leadsTo.map { l -> Link(l, 1) }) }
        val graph = Graph(nodes)

        val toPrune = graph.nodes.filter { it.item == 0 && it.name != "AA" }.map { it.name }
        val prunedGraph = graph.pruning(toPrune)



        return -1
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

    class Graph<T>(val nodes: List<Node<T>>) {
        private val nodeMap: Map<String, Node<T>> = nodes.associateBy { it.name }

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
}