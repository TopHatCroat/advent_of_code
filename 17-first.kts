import java.util.*

var input = 369
var position = 0
var list = mutableListOf(0)

fun MutableList<Int>.circularInsert(step: Int, value: Int) {
    position = (position + step) % list.size
    add(++position, value)
}

(1 until 2018).forEach {
    list.circularInsert(input, it)
}

println(list)

list.forEachIndexed { i, v ->
    if (v == 2017)
        println(list[i + 1])
}