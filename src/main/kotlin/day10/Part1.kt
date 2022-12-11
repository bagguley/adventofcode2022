package day10

fun main() {
    println(Part1.calc(testData))
    println(Part1.oneLine(testData))
    println(Part1.calc(data))
    println(Part1.oneLine(data))
}

object Part1 {
    fun calc(input: List<String>): Int {
        var x = 1

        val p = sequence {
            for (i in input) {
                if (i == "noop") yield(x)
                else {
                    val toAdd = i.split(" ")[1].toInt()
                    yield(x)
                    yield(x)
                    x += toAdd
                }
            }
        }

        val q = sequence {
            p.forEachIndexed { index, i ->
                if (index + 1 == 20 || (index + 21) % 40 == 0) {
                    yield ((index + 1) * i)
                }
            }
        }

        return q.sum()
    }

    fun oneLine(a: List<String>): Int {
        return a.flatMap{if(it=="noop")listOf(0)else listOf(0,it.split(" ")[1].toInt())}.runningFold(1){acc,i->i+acc}.mapIndexed{x,i->(x+1)*i}.filterIndexed{x,_->(x-19)%40==0}.sum()
    }
}