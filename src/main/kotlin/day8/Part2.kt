package day8

fun main() {
    println(Part2.calc(testData))
    println(Part2.calc(data))
}

object Part2 {
    fun calc(data: List<String>): Int {
        val visible = mutableSetOf<Tree>()
        visible.addAll(visibleFromTop(data))

        visibleFromRight(data).forEach {v ->
            val f = visible.find{ x -> x.coords == v.coords}
            if (f == null) {
                visible.add(v)
            } else {
                f.views.addAll(v.views)
            }
        }

        visibleFromBottom(data).forEach {v ->
            val f = visible.find{ x -> x.coords == v.coords}
            if (f == null) {
                visible.add(v)
            } else {
                f.views.addAll(v.views)
            }
        }

        visibleFromLeft(data).forEach {v ->
            val f = visible.find{ x -> x.coords == v.coords}
            if (f == null) {
                visible.add(v)
            } else {
                f.views.addAll(v.views)
            }
        }

        val tree = visible.maxBy { t -> t.visible() }
        return tree.visible()
    }

    private fun visibleFromLeft(input: List<String>): Set<Tree> {
        val width = input[0].length - 1
        return input.mapIndexed { y, s ->
            val result = mutableListOf<Tree>()
            var x = 0
            val views = mutableListOf<Int>()
            while (x <= width) {
                val nextTreeHeight = s[x].digitToInt()
                result.add(Tree(Pair(x, y), nextTreeHeight, views.toList().reversed()))
                views.add(nextTreeHeight)
                x++
            }
            result
        }.flatten().toSet()
    }

    private fun visibleFromRight(input: List<String>): Set<Tree> {
        val width = input[0].length - 1
        return input.mapIndexed { y, s ->
            val result = mutableListOf<Tree>()
            var x = width
            val views = mutableListOf<Int>()
            while (x >= 0) {
                val nextTreeHeight = s[x].digitToInt()
                result.add(Tree(Pair(x, y), nextTreeHeight, views.toList().reversed()))
                views.add(nextTreeHeight)
                x--
            }
            result
        }.flatten().toSet()
    }

    private fun visibleFromTop(input: List<String>): Set<Tree> {
        val result = mutableSetOf<Tree>()
        val height = input.size - 1
        val width = input[0].length - 1

        for (x in 0 ..  width) {
            val views = mutableListOf<Int>()
            for (y in 0 .. height) {
                val nextTreeHeight = input[y][x].digitToInt()
                result.add(Tree(Pair(x, y), nextTreeHeight, views.toList().reversed()))
                views.add(nextTreeHeight)
            }
        }
        return result
    }

    fun visibleFromBottom(input: List<String>): Set<Tree> {
        val result = mutableSetOf<Tree>()
        val height = input.size - 1
        val width = input[0].length - 1

        for (x in 0 .. width) {
            val views = mutableListOf<Int>()
            for (y in height downTo 0) {
                val nextTreeHeight = input[y][x].digitToInt()
                result.add(Tree(Pair(x, y), nextTreeHeight, views.toList().reversed()))
                views.add(nextTreeHeight)
            }
        }
        return result
    }
}

class Tree(val coords: Pair<Int, Int>, private val height: Int, views: List<Int>) {
    val views: MutableList<List<Int>> = mutableListOf()

    init {
        this.views.add(views)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tree

        if (coords != other.coords) return false

        return true
    }

    override fun hashCode(): Int {
        return coords.hashCode()
    }

    fun visible(): Int {
        return views.map { view ->
            var count = 0
            for (c in view) {
                count++
                if (c >= height) break
            }
            count
        }.reduce { acc, i -> acc * i }
    }
}