import java.util.*

val input = "14	0	15	12	11	11	3	5	1	6	8	4	9	1	8	4"
val inputArr = input.split("\t", " ").filter { !it.isNullOrBlank() }.map { it.toInt() }.toIntArray()
val history = LinkedList<IntArray>()

while (history.find { it.contentEquals(inputArr) }?.isEmpty() != false) {
    history.add(inputArr.copyOf())

    var max = inputArr.indexOfFirst { it == inputArr.max() }

    var block = inputArr[max]
    inputArr[max] = 0

    while (block > 0) {
        max++
        inputArr[max % inputArr.size]++
        block--
    }
    println(Arrays.toString(inputArr))
}

println("Size of history: ${history.size}")
println("Blocks between same states: ${history.size - history.indexOfFirst { it.contentEquals(inputArr) }}")