import java.io.File
import java.util.*
import kotlin.math.abs

data class IntPair(var x: Int = 0, var y: Int = 0) {
    fun add(dir: IntPair) {
        this.x += dir.x
        this.y += dir.y
    }
}

fun parseDirection(name: String): IntPair = when (name) {
    "sw" -> SOUTH_WEST
    "nw" -> NORTH_WEST
    "s" -> SOUTH
    "n" -> NORTH
    "se" -> SOUTH_EAST
    "ne" -> NORTH_EAST
    else -> IntPair()
}

val input = File("inputs/11.txt").readText(charset("UTF-8")).split(",")
val SOUTH_WEST = IntPair(-1, 0)
val NORTH_WEST = IntPair(0, 1)
val SOUTH = IntPair(-1, -1)
val NORTH = IntPair(1, 1)
val SOUTH_EAST = IntPair(0, -1)
val NORTH_EAST = IntPair(1, 0)

val stepDistances = LinkedList<Int>()
var destination = IntPair()
println("This will take some time")
input.forEach {
    destination.add(parseDirection(it))
    stepDistances.add(aStar(IntPair(0, 0), destination).size - 1)
    println("Completed ${stepDistances.size}/${input.size}")
}

println("Furthest destination was ${stepDistances.max()}")
println("Final destination is ${aStar(IntPair(0, 0), destination).size - 1}")


fun aStar(start: IntPair, goal: IntPair): List<IntPair> {
    val closedSet = HashSet<IntPair>()
    val openSet = HashSet<IntPair>().also { it.add(start) }
    val cameFrom = HashMap<IntPair, IntPair>()
    val gScore = HashMap<IntPair, Int>().also { it.put(start, 0) }
    val fScore = HashMap<IntPair, Int>().also { it.put(start, heuristicCost(start, start)) }

    while (openSet.isNotEmpty()) {
        val current = openSet.minWith(Comparator { t1, t2 -> fScore[t1]!!.compareTo(fScore[t2]!!) })!!
        if (current == goal) {
            return reconstructPath(cameFrom, current)
        }

        openSet.remove(current)
        closedSet.add(current)

        neighboursOf(current).forEach {
            if (closedSet.contains(it))
                return@forEach

            if (!openSet.contains(it))
                openSet.add(it)

            var gTentative = gScore[current] ?: Int.MAX_VALUE + distanceBetween(current, it)

            if (gTentative >= gScore[it] ?: Int.MAX_VALUE)
                return@forEach

            cameFrom[it] = current
            gScore[it] = gTentative
            fScore[it] = gTentative + heuristicCost(it, goal)
        }
    }

    return emptyList()
}

fun neighboursOf(dr: IntPair): List<IntPair> {
    return listOf(
            dr.copy().also { it.add(SOUTH) },
            dr.copy().also { it.add(SOUTH_EAST) },
            dr.copy().also { it.add(SOUTH_WEST) },
            dr.copy().also { it.add(NORTH) },
            dr.copy().also { it.add(NORTH_EAST) },
            dr.copy().also { it.add(NORTH_WEST) })
}

fun heuristicCost(start: IntPair, goal: IntPair): Int = manhattanDistance(start, goal)

fun distanceBetween(current: IntPair, it: IntPair): Int = 0

fun manhattanDistance(start: IntPair, end: IntPair): Int {
    return abs(end.x - start.x) + abs(end.y - start.y)
}

fun reconstructPath(cameFrom: HashMap<IntPair, IntPair>, current: IntPair): List<IntPair> {
    var cur = current
    val finalPath = mutableListOf(cur)

    while (cameFrom.containsKey(cur)) {
        cur = cameFrom[cur]!!
        finalPath.add(cur)
    }
    return finalPath
}
