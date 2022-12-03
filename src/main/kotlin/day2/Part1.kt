package day2

fun main() {
    println(data.sumOf{Part1.score(it)})
}

object Part1 {
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
                    'X' -> DRAW + ROCK
                    'Y' -> PAPER + WIN
                    'Z' -> SCISSORS + LOSE
                    else -> {throw RuntimeException("Oops $you")}
                }
            'B' ->
                when(you) {
                    'X' -> LOSE + ROCK
                    'Y' -> PAPER + DRAW
                    'Z' -> SCISSORS + WIN
                    else -> {throw RuntimeException("Oops $you")}
                }
            'C' ->
                when(you) {
                    'X' -> WIN + ROCK
                    'Y' -> PAPER + LOSE
                    'Z' -> SCISSORS + DRAW
                    else -> {throw RuntimeException("Oops $you")}
                }
            else -> {throw RuntimeException("Oops $op")}
        }
    }
}