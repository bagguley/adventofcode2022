package day11

fun main() {
    Part1.calc(testData)
    Part1.calc(data)
}

object Part1 {
    fun calc(input: List<String>) {
        val game = Game(input)
        game.play(20)
        println(game.monkeys.map{it.inspectionCount}.sorted().takeLast(2).reduce {a, b -> a * b})
    }
}

class Game(input: List<String>) {
    val monkeys: List<Monkey> = input.map{
        val lines = it.split("\n")
        val startingItems = lines[1].substring(18).split(",").map{it.trim().toInt()}.toMutableList()
        val operation = lines[2].substring(19)
        val ifDivisibleBy = lines[3].substring(21).toInt()
        val ifTrue = lines[4].substring(29).toInt()
        val ifFalse = lines[5].substring(30).toInt()
        Monkey(startingItems, operation, ifDivisibleBy, ifTrue, ifFalse)
    }

    fun throwTo(worry : Int, monkey: Int) {
        monkeys[monkey].throwTo(worry)
    }

    fun play(rounds: Int) {
        for (i in 1..rounds) {
            for (m in monkeys) {
                m.turn(this)
            }
        }
    }
}

class Monkey(private val items: MutableList<Int>, private val operation: String, private val ifDivisibleBy: Int, private val ifTrue: Int, private val ifFalse: Int) {
    var inspectionCount = 0

    fun turn(game: Game) {
        for (i in items) {
            inspectionCount++
            val worry = operation(i) / 3

            when (test(worry)) {
                true -> game.throwTo(worry, ifTrue)
                false -> game.throwTo(worry, ifFalse)
            }
        }
        items.clear()
    }

    fun throwTo(value: Int) {
        items.add(value)
    }

    private fun test(value: Int): Boolean {
        return value % ifDivisibleBy == 0
    }

    private fun operation(value: Int): Int {
        val tokens = operation.split(" ")
        val first = tokens[0]
        val op = tokens[1]
        val second = tokens[2]

        val a = if (first == "old") value else first.toInt()
        val b = if (second == "old") value else second.toInt()

        return when(op) {
            "+" -> a + b
            "*" -> a * b
            else -> throw RuntimeException("Bad op")
        }
    }
}