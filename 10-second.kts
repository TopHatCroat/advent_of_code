import java.util.*

var input = "157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30"
var endPadding = listOf(17, 31, 73, 47, 23)
var numbers = IntArray(256, { i -> i })
var pos = 0
var skip = 0

var padded: MutableList<Int> = input.map { it.toInt() }.toMutableList()
padded.addAll(endPadding)
(0..63).forEach {
    padded.forEach {
        numbers.reverseSubstring(pos, it-1)
        pos += it + skip
        skip++
    }
}

numbers.asSequence().chunked(16).map { it.reduce { acc, i -> acc xor i } }.forEach { print(formatted(it)) }


fun IntArray.reverseSubstring(start: Int, offset: Int) {
    (0..offset/2).forEach {
        val old = this[(start + it) % this.size]
        this[(start + it) % this.size] = this[(start + offset - it) % this.size]
        this[(start + offset - it) % this.size] = old
    }
}

fun formatted(it: Int): String = if(it.toString(16).length == 2) it.toString(16) else "0" + it.toString(16)
