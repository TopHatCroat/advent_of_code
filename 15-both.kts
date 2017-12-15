data class Generator(val factor: Long = 0, val start: Int = 0, val selector: Int = 0) {

    var prev = start

    fun next(): Int {
        prev = ((prev * factor) % Int.MAX_VALUE).toInt()
        return prev
    }

    fun nextSelective(): Int {
        next()
        if(prev % selector != 0)
            nextSelective()

        return prev
    }

    fun reset() {
        prev = start
    }
}

fun Int.toBin(): String {
    return toString(2).padStart(32, '0')
}

val genA = Generator(16807L, 703, 4)
val genB = Generator(48271L, 516, 8)

var count = 0
repeat(40 * 1000000 - 1) {
    if(genA.next().toBin().drop(16) == genB.next().toBin().drop(16))
        count++
}

println("There are $count with equal last bits")

genA.reset()
genB.reset()
count = 0
repeat(5 * 1000000 - 1) {
    if(genA.nextSelective().toBin().drop(16) == genB.nextSelective().toBin().drop(16))
        count++
}

println("There are $count with equal last bits in selective mode")
