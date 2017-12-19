import java.io.File

typealias IntPair = Pair<Int, Int>
typealias Network = List<List<String>>
fun Network.getPosition(pair: IntPair): String? {
    return try {
        get(pair.y())[pair.x()]
    } catch (t: Throwable) {
        null
    }
}

fun IntPair.x() = first
fun IntPair.y() = second
fun IntPair.plus(pair: IntPair): IntPair {
    return IntPair(first + pair.first, second + pair.second)
}

val RIGHT = IntPair(-1, 0)
val UP = IntPair(0, -1)
val DOWN = IntPair(0, 1)
val LEFT = IntPair(1, 0)

var input: Network = File("inputs/19.txt").readLines(charset("UTF-8")).map { it.split("") }
var letters = ""

var position = IntPair(input[0].indexOfFirst { "|" == it }, 0)
var direction = DOWN
var count = 0

follow(position, direction)

println("Letters the packet encoutered are: $letters")
println("Total bumber of steps is $count")

tailrec fun follow(position: IntPair, direction: IntPair) {
    val current = input.getPosition(position) ?: return
    if(current.isBlank())
        return

    count++
    if (current[0] in 'A'..'Z') {
        letters += current
        return follow(position.plus(direction), direction)
    }

    if (current == "+") {
        if (direction in arrayOf(DOWN, UP)) {
            if ( "-" == input.getPosition(position.plus(LEFT)))
                return follow(position.plus(LEFT), LEFT)
            if ( "-" == input.getPosition(position.plus(RIGHT)))
                return follow(position.plus(RIGHT), RIGHT)
        } else {
            if ("|" == input.getPosition(position.plus(UP)))
                return follow(position.plus(UP), UP)
            if ("|" == input.getPosition(position.plus(DOWN)))
                return follow(position.plus(DOWN), DOWN)
        }
    } else
        return follow(position.plus(direction), direction)
}

