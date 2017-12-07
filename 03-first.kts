import sun.security.util.Length
import java.util.stream.Collectors
import kotlin.math.abs

val input = 20
val ARRAY_SIZE = 6
val RIGHT = IntPair(0, 1)
val UP = IntPair(1, 0)
val DOWN = IntPair(-1, 0)
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
var counter = 1

while (pos.x < ARRAY_SIZE && pos.y < ARRAY_SIZE) {
    val direction = direction(i % 4)

    (0 until step).forEach {
        println(counter.toString() + " " + pos.x.toString() + ", " + pos.y)
        memory[pos.y][pos.x] = counter
        pos.add(direction)
        counter++
    }
    printArray(memory)
    if(i % 2 == 0) step++
    i++
}

print(manhattanDistance(find(input), IntPair(ARRAY_SIZE / 2, ARRAY_SIZE / 2)))

fun manhattanDistance(start: IntPair, end: IntPair): Int {
    return abs(end.x - start.x) + abs(end.y - start.y)
}

fun find(num: Int): IntPair {
    (0 until ARRAY_SIZE).forEach { x ->
        (0 until ARRAY_SIZE).forEach { y ->
            if(memory[x][y] == num)
                return IntPair(x, y)
        }
    }
    return IntPair(-1,-1)
}

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
        it.forEach { print(it.toString() + ", ") }
        print("],\n")
    }
    print("]")

}