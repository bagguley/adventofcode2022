package day10

fun main() {
    Part2.calc(testData)
    println()
    Part2.oneLine(testData)
    println()
    Part2.calc(data)
    println()
    Part2.oneLine(data)
}

object Part2 {
    fun calc(input: List<String>){
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

        p.forEachIndexed { index, y ->
            if (index % 40 == 0) println()

            if ( (index + 1) % 40 >= y && (index + 1) % 40 <= y + 2) print("#") else print(".")
        }
    }

    fun oneLine(a: List<String>) {
        a.flatMap{if(it=="noop")listOf(0)else listOf(0,it.split(" ")[1].toInt())}.runningFold(1){acc,i->i+acc}.forEachIndexed{x,i->if(x%40==0)println();if((x+1)%40>=i&&(x+1)%40<=i+2)print("#")else print(".")}
    }
}