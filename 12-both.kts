import java.io.File
import java.util.*
import kotlin.collections.HashSet

data class Entry(var value: Int = 0, var accessible: List<Int> = emptyList()) {
    val delimiter = " <-> "

    constructor(s: String) : this() {
        s.split(delimiter).let {
            value = it[0].toInt()
            accessible = it[1].split(", ").map { it.toInt() }
        }
    }

}

val input = File("inputs/12.txt").readLines(charset("UTF-8")).map { Entry(it) }
val rootAccessible: MutableSet<Int> = HashSet()
val allGroups = ArrayList<Set<Int>>()

input.forEach { elem ->
    rootAccessible.clear()

    var found = false
    allGroups.forEach { if (it.contains(elem.value)) found = true }
    if (found) return@forEach

    link(elem.value, input, rootAccessible)
    allGroups.add(HashSet(rootAccessible))
}

println("There are ${allGroups.size} grops")
println("Group 0 has ${allGroups[0].size} elements")

fun link(elem: Int, input: List<Entry>, out: MutableSet<Int>) {
    val e = input.find { it.value == elem }
    out.add(e?.value ?: 0)
    e?.accessible?.forEach {
        if (!out.contains(it))
            link(it, input, out)
    }
}