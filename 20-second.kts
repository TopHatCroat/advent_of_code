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

    fun update() {
        (0..2).forEach { vel[it] += acc[it] }
        (0..2).forEach { pos[it] += vel[it] }
    }

    fun isOnSamePosition(entry: Entry): Boolean {
        return pos[0] == entry.pos[0] && pos[1] == entry.pos[1] && pos[1] == entry.pos[1]
    }
}

val MAGIC_NUMBER = 50
var input = File("inputs/20.txt").readLines(charset("UTF-8")).mapIndexed { i, s -> Entry.new(i, s) } as MutableList<Entry>
var count = 0

repeat(MAGIC_NUMBER) {
    input.forEach { it.update() }

    var theSame = ArrayList<Entry>()

    input.forEach { entry ->
        if (theSame.contains(entry).not())
            input.filter { entry.isOnSamePosition(it) }.let {
                if (it.size > 1) theSame.addAll(it)
            }
    }
    input.removeAll(theSame)
}

println(input.size)