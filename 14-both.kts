import java.lang.Integer.parseInt

data class IntPair(var x: Int = 0, var y: Int = 0)

var endPadding = listOf(17, 31, 73, 47, 23)
val input = "vbqugkhl"

var memory = Array(128, { i -> "$input-$i" }).map { knotHash(it) }.map {
    it.map { parseInt(it.toString(), 16).toString(2) }
            .joinToString(transform = { it.padStart(4, '0') }, separator = "")
}

var groups = ArrayList<List<IntPair>>()
var filled = ArrayList<IntPair>()

(0 until memory.size).forEach { y ->
    (0 until memory[y].length).forEach { x ->
        if (memory[x][y] == '1')
            filled.add(IntPair(x, y))
    }
}

println("There are ${filled.size} squares used")
while(filled.isNotEmpty()) {
    var group = ArrayList<IntPair>()
    group(memory, filled[0].x, filled[0].y, group)
    filled.removeAll(group)
    groups.add(group)
}

println("There are ${groups.size} regioins")

fun group(source: List<String>, x: Int, y: Int, out: MutableList<IntPair>): Boolean {
    if (source[x][y] == '0' || out.contains(IntPair(x, y)))
        return false
    else
        out.add(IntPair(x, y))

    try {
        group(source, x + 1, y, out)
    } catch (e: Throwable) {
    }
    try {
        group(source, x - 1, y, out)
    } catch (e: Throwable) {
    }
    try {
        group(source, x, y + 1, out)
    } catch (e: Throwable) {
    }
    try {
        group(source, x, y - 1, out)
    } catch (e: Throwable) {
    }

    return true
}

fun knotHash(str: String): String {
    var numbers = IntArray(256, { i -> i })
    var pos = 0
    var skip = 0
    var padded: MutableList<Int> = str.map { it.toInt() }.toMutableList().also { it.addAll(endPadding) }
    repeat(64) {
        padded.forEach {
            numbers.reverseSubstring(pos, it - 1)
            pos += it + skip
            skip++
        }
    }

    return numbers.asSequence().chunked(16)
            .map { it.reduce { acc, i -> acc xor i } }
            .joinToString(transform = { it.toString(16).padStart(2, '0') }, separator = "")
}

fun IntArray.reverseSubstring(start: Int, offset: Int) {
    (0..offset / 2).forEach {
        val old = this[(start + it) % this.size]
        this[(start + it) % this.size] = this[(start + offset - it) % this.size]
        this[(start + offset - it) % this.size] = old
    }
}