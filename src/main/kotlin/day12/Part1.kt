package day12

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        val world = World(input)
        return world.climb()
    }
}

class World(private val map: List<String>, private val start: Char = 'S', private val end: Char = 'E') {
    private val scoreMap = mutableMapOf<Pair<Int, Int>, Int>()
    private val movements = listOf(Pair(1,0), Pair(0,1), Pair(-1,0), Pair(0,-1))
    private val width: Int = map[0].length
    private val height: Int = map.size

    fun climb(): Int {
        val start = find(start)
        val end = find(end)

        val toVisit = mutableListOf(start)
        scoreMap[start] = 0

        while(true) {
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
            .filter { currentChar == start || (map[it.second][it.first] == end && currentChar == 'z') ||
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
}