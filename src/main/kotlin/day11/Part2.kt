package day11

fun main() {
    Part2.calc(testData)
    Part2.calc(data)
}

object Part2 {
    fun calc(input: List<String>) {
        val game = Game2(input)

        val divisor = game.monkeys.map { it.ifDivisibleBy }.reduce { a, b -> a * b }

        game.play(10000, divisor)

        println(game.monkeys.map{it.inspectionCount}.sorted().takeLast(2).reduce {a, b -> a * b})
    }
}

class Game2(input: List<String>) {
    val monkeys: List<Monkey2> = input.map{
        val lines = it.split("\n")
        val startingItems = lines[1].substring(18).split(",").map{it.trim().toLong()}.toMutableList()
        val operation = lines[2].substring(19)
        val ifDivisibleBy = lines[3].substring(21).toLong()
        val ifTrue = lines[4].substring(29).toInt()
        val ifFalse = lines[5].substring(30).toInt()
        Monkey2(startingItems, operation, ifDivisibleBy, ifTrue, ifFalse)
    }

    fun throwTo(worry : Long, monkey: Int) {
        monkeys[monkey].throwTo(worry)
    }

    fun play(rounds: Int, divisor: Long) {
        for (i in 1..rounds) {
            for (m in monkeys) {
                m.turn(this, divisor)
            }
        }
    }
}

class Monkey2(private val items: MutableList<Long>, private val operation: String, val ifDivisibleBy: Long, private val ifTrue: Int, private val ifFalse: Int) {
    var inspectionCount = 0L

    fun turn(game: Game2, divisor: Long) {
        for (i in items) {
            inspectionCount++
            val worry = operation(i) % divisor

            when (test(worry)) {
                true -> game.throwTo(worry, ifTrue)
                false -> game.throwTo(worry, ifFalse)
            }
        }
        items.clear()
    }

    fun throwTo(value: Long) {
        items.add(value)
    }

    private fun test(value: Long): Boolean {
        return value % ifDivisibleBy == 0L
    }

    private fun operation(value: Long): Long {
        val tokens = operation.split(" ")
        val first = tokens[0]
        val op = tokens[1]
        val second = tokens[2]

        val a = if (first == "old") value else first.toLong()
        val b = if (second == "old") value else second.toLong()

        return when(op) {
            "+" -> a + b
            "*" -> a * b
            else -> throw RuntimeException("Bad op")
        }
    }
}