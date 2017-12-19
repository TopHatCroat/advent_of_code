import java.io.File
import kotlin.system.exitProcess

var input = File("inputs/18.txt").readLines(charset("UTF-8"))
var lastSound: Long = 0

var registers = HashMap<String, Long>()
var i = 0
while (i in 0..input.size) {
    input[i].split(" ").let {
        when (it[0]) {
            "snd" -> {
                lastSound = parseValue(it[1])
                println("Playing sound from ${it[1]} at freq: $lastSound")
            }
            "set" -> registers[it[1]] = parseValue(it[2])
            "add" -> registers[it[1]] = parseValue(it[1]) + parseValue(it[2])
            "mul" -> registers[it[1]] = parseValue(it[1]) * parseValue(it[2])
            "mod" -> registers[it[1]] = parseValue(it[1]) % (parseValue(it[2]))
            "rcv" -> if(parseValue(it[1]) != 0L) {
                println("Recovered last frequency: $lastSound")
                exitProcess(0)
            }
            "jgz" -> if(parseValue(it[1]) > 0L) i = i + parseValue(it[2]).toInt() - 1

        }
    }
    i++
}

fun parseValue(v: String): Long = if (v[0] in 'a'..'z') registers.getOrDefault(v, 0) else v.toLong()