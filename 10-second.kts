import java.util.*

var input = "157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30"
var endPadding = listOf(17, 31, 73, 47, 23)
var numbers = IntArray(256, { i -> i })
var pos = 1
var skip = 0

var padded: MutableList<Int> = input.map { it.toInt() }.toMutableList().also { it.addAll(endPadding) }
repeat(64) {
    padded.forEach {
        numbers.reverseSubstring(pos, it-1)
        pos += it + skip
        skip++
    }
}

numbers.asSequence().chunked(16).map { it.reduce { acc, i -> acc xor i } }.forEach { print(it.toString(16).padStart(2, '0')) }


fun IntArray.reverseSubstring(start: Int, offset: Int) {
    (0..offset/2).forEach {
        val old = this[(start + it) % this.size]
        this[(start + it) % this.size] = this[(start + offset - it) % this.size]
        this[(start + offset - it) % this.size] = old
    }
}
