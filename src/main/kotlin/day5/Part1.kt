package day5

fun main() {
    println(Part1.calc(crates, moves))
}

object Part1 {
    fun calc(input: List<MutableList<Char>>, instructions: List<List<Int>>): String {
        return input.also{instructions.forEach{i->it[i[2]-1].addAll(0,it[i[1]-1].removeFirst(i[0]))}}.map{it.first()}.foldRight(""){c,acc->c+acc}
    }
}

private fun <T> MutableList<T>.removeFirst(n: Int): List<T> {
    val result = this.take(n).reversed()
    this.subList(0, n).clear()
    return result
}