import java.io.File

fun spin(string: String, x: Int): String {
    return string.substring(string.length - x, string.length) + string.substring(0, string.length - x)
}

fun exchange(string: String, a: Int, b: Int): String {
    return string.toCharArray().apply { this[a] = this[b].also { this[b] = this[a] } }.joinToString(separator = "")
}

var programs: String = (0 until 16).map { 'a' + it }.joinToString(separator = "")
val input = File("inputs/16.txt").readText(charset("UTF-8")).split(",")
var iterations = ArrayList<String>()

while (iterations.size <= 1000000000) {
    input.forEach {
        programs = when {
            it[0] == 's' -> spin(programs, it.drop(1).toInt())
            it[0] == 'x' -> it.drop(1).split("/").let {
                exchange(programs, it[0].toInt(), it[1].toInt())
            }
            it[0] == 'p' -> it.drop(1).split("/").let {
                exchange(programs, programs.indexOf(it[0]), programs.indexOf(it[1]))
            }
            else -> programs
        }
    }

    if (programs !in iterations)
        iterations.add(programs)
    else
        break
}

println("First iteration ${iterations[0]}")
println("After all iterations ${iterations[(1000000000 % iterations.size) - 1]}")