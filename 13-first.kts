import java.io.File

data class Scanner(var x: Int = 0, var range: Int = 0, var position: Int = 0)

fun Scanner.move(iteration: Int) {
    if ((iteration / (range - 1)) % 2 == 0) position++ else position--
}

val input = File("inputs/13.txt").readLines(charset("UTF-8")).map { it.split(": ") }.map { Scanner(it[0].toInt(), it[1].toInt()) }
//val input = "0: 3\n1: 2\n4: 4\n6: 4".split("\n").map { it.split(": ") }.map { Scanner(it[0].toInt(), it[1].toInt()) }

val scannerPositions = IntArray(input.size, { _ -> 0 })
var severity = 0
0.until(input.last().x).forEach { i ->
    input.find { it.x == i }?.let {
        if (it.position == 0) severity += it.x * it.range
    }
    input.forEach { it.move(i) }
}

println(severity)