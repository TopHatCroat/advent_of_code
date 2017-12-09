import java.io.File

data class Entry(var register: String = "", var add: Boolean = true, var amount: Int = 0,
                 var param: String = "", var comparator: String = "", var value: Int = 0) {

    private val regex = Regex("(\\w*) (inc|dec) (-?\\d*) if (\\w*) ([(!|<|>|=)]{1,2}) (-?\\d*)")

    constructor(string: String) : this() {
        regex.find(string)?.groupValues?.let {
            register = it[1]
            add = it[2] == "inc"
            amount = it[3].toInt()
            param = it[4]
            comparator = it[5]
            value = it[6].toInt()
        }
    }
}

var input = File("inputs/08.txt").readLines(charset("UTF-8"))
        .map { Entry(it) }

val registers = HashMap<String, Int>()
var max = 0

input.forEach {
    if(compare(registers.getOrDefault(it.param, 0), it.comparator, it.value)) {
        val current = registers.getOrDefault(it.register, 0)
        registers.put(it.register, (if(it.add) current + it.amount else current - it.amount))
    }

    max = if(max < registers.values.max()?:0) registers.values.max()?:0 else max
}

println("Highest ever value is: $max")
println("Current highest value is: ${registers.values.max()}")

fun compare(first: Int, comparator: String, second: Int) = when(comparator) {
    "<" -> first < second
    ">" -> first > second
    ">=" -> first >= second
    "<=" -> first <= second
    "==" -> first == second
    "!=" -> first != second
    else -> false
}
