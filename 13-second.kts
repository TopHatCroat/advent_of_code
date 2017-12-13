import java.io.File

data class Scanner(var x: Int = 0, var range: Int = 0, var position: Int = 0)

val input = File("inputs/13.txt").readLines(charset("UTF-8")).map { it.split(": ") }.map { Scanner(it[0].toInt(), it[1].toInt()) }

var waitTime = 1
while (input.any { (it.x + waitTime) % (2 * (it.range - 1)) == 0 }) waitTime++

println("Severity 0 if waiting for $waitTime picoseconds")
