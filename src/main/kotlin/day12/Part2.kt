package day12

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(input: List<String>): Int {
        val world = World2(input)
        return world.minClimb()
    }
}

class World2(private val map: List<String>, private val end: Char = 'E') {
    private val movements = listOf(Pair(1,0), Pair(0,1), Pair(-1,0), Pair(0,-1))
    private val width: Int = map[0].length
    private val height: Int = map.size

    fun minClimb(): Int {
        val starts = findAllA()
        return starts.minOf { climb(it) }
    }

    private fun climb(start: Pair<Int,Int>): Int {
        val scoreMap = mutableMapOf<Pair<Int, Int>, Int>()

        val end = find(end)

        val toVisit = mutableListOf(start)
        scoreMap[start] = 0

        while(true) {
            if (toVisit.isEmpty()) return Int.MAX_VALUE
            val nextPos = toVisit.minByOrNull { scoreMap[it]!! }!!
            toVisit.remove(nextPos)
            val nextScore = scoreMap[nextPos]!! + 1

            val neighbours = neighbours(nextPos)
            neighbours.removeAll(scoreMap.keys)
            toVisit.addAll(neighbours)

            neighbours.forEach {
                scoreMap[it] = nextScore
            }
            if (nextPos == end) return scoreMap[nextPos]!!
        }
    }

    private fun neighbours(currentPos: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
        val currentChar = map[currentPos.second][currentPos.first]
        return movements.map{ Pair(currentPos.first + it.first, currentPos.second + it.second) }
            .filter { it.first > -1 && it.second > -1 }
            .filter { it.first < width && it.second < height }
            .filter { (map[it.second][it.first] == end && currentChar == 'z') ||
                    (map[it.second][it.first].code >= 'a'.code) && (map[it.second][it.first].code - currentChar.code) < 2 }
            .toMutableList()
    }

    private fun find(char: Char): Pair<Int,Int> {
        for (y in map.indices) {
            val x = map[y].indexOf(char)
            if (x > -1) return Pair(x, y)
        }
        throw RuntimeException("Char '$char' not found")
    }

    private fun findAllA(): List<Pair<Int,Int>> {
        return sequence {
            for (y in map.indices) {
                val x = map[y].indexOf('a')
                if (x > -1) yield(Pair(x, y))
            }
        }.toList()
    }
}