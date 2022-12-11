package day8

fun main() {
    println(Part1.calc(testData))
    println(Part1.calc(data))
}

object Part1 {
    fun calc(data: List<String>): Int {
        val visible = mutableSetOf<Pair<Int,Int>>()
        visible.addAll(visibleFromTop(data))
        visible.addAll(visibleFromRight(data))
        visible.addAll(visibleFromBottom(data))
        visible.addAll(visibleFromLeft(data))
        return visible.size
    }

    private fun visibleFromLeft(input: List<String>): Set<Pair<Int,Int>> {
        val width = input[0].length - 1
        return input.mapIndexed { y, s ->
            val result = mutableListOf<Pair<Int,Int>>()
            var maxHeight = -1
            var x = 0
            while (x <= width) {
                if (s[x].digitToInt() > maxHeight) {
                    result.add(Pair(x, y))
                    maxHeight = s[x].digitToInt()
                }
                x++
            }
            result
        }.flatten().toSet()
    }

    private fun visibleFromRight(input: List<String>): Set<Pair<Int,Int>> {
        val width = input[0].length - 1
        return input.mapIndexed { y, s ->
            val result = mutableListOf<Pair<Int,Int>>()
            var maxHeight = -1
            var x = width
            while (x >= 0) {
                if (s[x].digitToInt() > maxHeight) {
                    result.add(Pair(x, y))
                    maxHeight = s[x].digitToInt()
                }
                x--
            }
            result
        }.flatten().toSet()
    }

    private fun visibleFromTop(input: List<String>): Set<Pair<Int,Int>> {
        val result = mutableSetOf<Pair<Int,Int>>()
        val height = input.size - 1
        val width = input[0].length - 1

        for (x in 0 ..  width) {
            var maxHeight = -1
            for (y in 0 .. height) {
                if (input[y][x].digitToInt() > maxHeight) {
                    result.add(Pair(x, y))
                    maxHeight = input[y][x].digitToInt()
                }
            }
        }
        return result
    }

    private fun visibleFromBottom(input: List<String>): Set<Pair<Int,Int>> {
        val result = mutableSetOf<Pair<Int,Int>>()
        val height = input.size - 1
        val width = input[0].length - 1

        for (x in 0 .. width) {
            var maxHeight = -1
            for (y in height downTo 0) {
                if (input[y][x].digitToInt() > maxHeight) {
                    result.add(Pair(x, y))
                    maxHeight = input[y][x].digitToInt()
                }
            }
        }
        return result
    }
}