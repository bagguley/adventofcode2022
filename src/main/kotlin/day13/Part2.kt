package day13

import java.util.*

fun main() {
    println(Part2.countOrdered(testData))
    println(Part2.countOrdered(data))
}

object Part2 {
    fun countOrdered(strings: List<List<String>>): Int {
        val loaded = load(strings).toMutableList()
        loaded.add(parse("[[2]]"))
        loaded.add(parse("[[6]]"))

        loaded.sortWith{a,b->compare(a,b)}

        return (loaded.indexOf(parse("[[2]]")) + 1) * (loaded.indexOf(parse("[[6]]")) + 1)
    }

    fun load(strings: List<List<String>>): List<MutableList<Any>> {
        return strings.flatMap { p -> p.map { s -> parse(s) } }
    }

    @Suppress("UNCHECKED_CAST")
    fun parse(string: String): MutableList<Any> {
        val list = mutableListOf<Any>()
        val stack = Stack<MutableList<Any>>()
        stack.push(list)
        parse(string, stack)
        return stack.first().first() as MutableList<Any>
    }

    fun parse(string: String, stack: Stack<MutableList<Any>>) {
        if (string.isEmpty()) {
            return
        }

        if (string[0] == ']') {
            stack.pop()
            parse(string.substring(1), stack)
        }

        if (string[0] == '[') {
            val newList = mutableListOf<Any>()
            stack.peek().add(newList)
            stack.add(newList)
            parse(string.substring(1), stack)
        }

        if (string[0] in "0123456789") {
            val next = string.takeWhile { it in "0123456789" }
            stack.peek().add(next.toInt())
            parse(string.substring(next.length), stack)
        }

        if (string[0] == ',') {
            parse(string.substring(1), stack)
        }
    }

    private fun compare(first: List<*>, second: List<*>): Int {
        var x = 0

        while (true) {
            if (x == first.size || x == second.size) {
                //if (first.size == second.size) return 0
                if (first.size < second.size) return -1
                if (first.size > second.size) return 1
                return 0
            }

            val a = first[x]
            val b = second[x]

            if (a is Int && b is Int) {
                if (a < b) return -1
                if (a > b) return 1
            }

            if (a is Int && b is List<*>) {
                val c = compare(listOf(a), b)
                if (c != 0) return c
            }

            if (a is List<*> && b is Int) {
                val c = compare(a, listOf(b))
                if (c != 0) return c
            }

            if (a is List<*> && b is List<*>) {
                val c = compare(a, b)
                if (c != 0) return c
            }

            x++
        }
    }
}
