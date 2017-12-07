import sun.security.util.Length
import java.util.stream.Collectors
import kotlin.math.abs

val input = 368078
val ARRAY_SIZE = 10
val RIGHT = IntPair(0, 1)
val UP = IntPair(-1, 0)
val DOWN = IntPair(1, 0)
val LEFT = IntPair(0, -1)

data class IntPair(var x: Int, var y: Int) {
    fun add(pair: IntPair) {
        this.x += pair.x
        this.y += pair.y
    }
}

var memory = Array<IntArray>(ARRAY_SIZE, { IntArray(ARRAY_SIZE) })
var pos: IntPair = IntPair(ARRAY_SIZE / 2, ARRAY_SIZE / 2)
var step = 1
var i = 1
var start = IntPair(-1, -1)
memory[pos.x][pos.y] = 1
while (pos.x < ARRAY_SIZE && pos.y < ARRAY_SIZE) {
    val direction = direction((i - 1) % 4)

    (0 until step).forEach {
        val adjecentSum = adjecentSum(pos)
//        println(adjecentSum.toString() + " " + pos.x.toString() + ", " + pos.y)
        memory[pos.x][pos.y] = adjecentSum
        if (start.x == -1 && adjecentSum > input) {
            start = IntPair(pos.x, pos.y)
        }
        pos.add(direction)
    }
    if (i % 2 == 0) step++
    i++
}
printArray(memory)
print(memory[start.x][start.y])
fun direction(i: Int): IntPair = when (i) {
    0 -> RIGHT
    1 -> UP
    2 -> LEFT
    3 -> DOWN
    else -> IntPair(0, 0)
}

fun printArray(memory: Array<IntArray>) {
    print("[\n")
    memory.forEach {
        print("  [")
        it.forEach {
            print(it.toString() + ", ")
            (it.toString().length until 10).forEach { print(" ") }
        }
        print("],\n")
    }
    print("]")

}

fun adjecentSum(position: IntPair): Int {
    var result = 0
    (position.x - 1 until position.x + 2).forEach { x ->
        if (x in 0..(ARRAY_SIZE - 1))
            (position.y - 1 until position.y + 2).forEach { y ->
                if (y in 0..(ARRAY_SIZE - 1))
                    result += memory[x][y]
            }
    }

    return result
}