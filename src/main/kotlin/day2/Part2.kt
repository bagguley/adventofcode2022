package day2

fun main() {
    println(data.sumOf{Part2.score(it)})
    println(data.sumOf{"348159267"[(it[0].code-65)*3+it[2].code-88].digitToInt()})
}

object Part2 {
    private const val DRAW = 3L
    private const val WIN = 6L
    private const val LOSE = 0L
    private const val ROCK = 1L
    private const val PAPER = 2L
    private const val SCISSORS = 3L

    fun score(it: String): Long {
        val op = it[0]
        val you = it[2]

        return when(op) {
            'A' ->
                when(you) {
                    'X' -> LOSE + SCISSORS
                    'Y' -> DRAW + ROCK
                    'Z' -> WIN + PAPER
                    else -> {throw RuntimeException("Oops $you")}
                }
            'B' ->
                when(you) {
                    'X' -> LOSE + ROCK
                    'Y' -> DRAW + PAPER
                    'Z' -> WIN + SCISSORS
                    else -> {throw RuntimeException("Oops $you")}
                }
            'C' ->
                when(you) {
                    'X' -> LOSE + PAPER
                    'Y' -> DRAW + SCISSORS
                    'Z' -> WIN + ROCK
                    else -> {throw RuntimeException("Oops $you")}
                }
            else -> {throw RuntimeException("Oops $op")}
        }
    }
}