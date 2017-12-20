import java.io.File
import kotlin.math.abs

data class Entry(val id: Int, var pos: MutableList<Long>, var vel: MutableList<Long>, var acc: MutableList<Long>) {
    companion object {
        private val regex = Regex("p=<(.*)>, v=<(.*)>, a=<(.*)>")

        fun new(id: Int, string: String): Entry {
            val groups = regex.find(string)?.groupValues!!
            return Entry(
                    id,
                    groups[1].split(",").map { it.toLong() }.toMutableList(),
                    groups[2].split(",").map { it.toLong() }.toMutableList(),
                    groups[3].split(",").map { it.toLong() }.toMutableList())
        }
    }

    fun distanceFromOrigin(): Long {
        return abs(pos[0]) + abs(pos[1]) + abs(pos[2])
    }

    fun update() {
        (0..2).forEach { vel[it] += acc[it] }
        (0..2).forEach { pos[it] += vel[it] }
    }
}

var ORIGIN = Entry(-1, mutableListOf(0, 0, 0), mutableListOf(0, 0, 0), mutableListOf(0, 0, 0))
val MAGIC_NUMBER = 400

var input = File("inputs/20.txt").readLines(charset("UTF-8")).mapIndexed { i, s -> Entry.new(i, s) }

var min = ORIGIN
var count = 0

while (true) {
    input.forEach { it.update() }
    val newMin = input.minBy { it.distanceFromOrigin() }!!

    if (min.id == newMin.id)
        count++
    else
        min = newMin.also { count = 0 }

    if (count > MAGIC_NUMBER) break
}

println(min)