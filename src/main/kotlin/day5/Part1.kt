package day5

fun main() {
    println(Part1.calc(crates, moves))
}

object Part1 {
    fun calc(input: List<MutableList<Char>>, instructions: List<List<Int>>): String {
        return input.also{instructions.forEach{i->it[i[2]-1].addAll(0,it[i[1]-1].let{x->x.take(i[0]).reversed().also{x.subList(0,i[0]).clear()}})}}.map{it.first()}.foldRight(""){c,a->c+a}
    }
}

