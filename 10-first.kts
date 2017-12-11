import java.util.*

var input = "157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30"
//var input = "3,4,1,5"
var numbers = IntArray(256, { i -> i })
var pos = 0
var skip = 0

input.split(",").map { it.toInt() }.forEach {
    numbers.reverseSubstring(pos, it-1)
    pos += it + skip
    skip++
}

println(Arrays.toString(numbers))
println("${numbers[0]} * ${numbers[1]} = ${numbers[0] * numbers[1]}")


fun IntArray.reverseSubstring(start: Int, offset: Int) {
    (0..offset/2).forEach {
        val old = this[(start + it) % this.size]
        this[(start + it) % this.size] = this[(start + offset - it) % this.size]
        this[(start + offset - it) % this.size] = old
    }
}