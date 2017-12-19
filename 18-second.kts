import java.io.File
import kotlin.system.exitProcess
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

data class Program(private val input: List<String>, private val id: Long) {
    var registers = HashMap<String, Long>()
    var sendCount = 0
    var receive = LinkedBlockingQueue<Long>()
    lateinit var other: Program
    var waiting = false

    fun process() {
        var i = 0
        while (i in 0 until input.size) {
            input[i].split(" ").let {
                when (it[0]) {
                    "snd" -> {
                        sendCount++
                        other.receive.put(parseValue(it[1]))
                    }
                    "set" -> registers[it[1]] = parseValue(it[2])
                    "add" -> registers[it[1]] = parseValue(it[1]) + parseValue(it[2])
                    "mul" -> registers[it[1]] = parseValue(it[1]) * parseValue(it[2])
                    "mod" -> registers[it[1]] = parseValue(it[1]) % (parseValue(it[2]))
                    "rcv" -> {
                        val poll = receive.poll(1, TimeUnit.SECONDS)
                        if (poll != null)
                            registers[it[1]] = poll
                        else {
                            println("$id DONE, sent $sendCount")
                            return
                        }
                    }
                    "jgz" -> {
                        if (parseValue(it[1]) > 0L) i = i + parseValue(it[2]).toInt() - 1
                    }

                }
            }
            i++
        }
    }

    fun parseValue(v: String): Long = if (v[0] in 'a'..'z') registers.getOrDefault(v, id) else v.toLong()
}

var input = File("inputs/18.txt").readLines(charset("UTF-8"))

var prog1 = Program(input, 0)
var prog2 = Program(input, 1)
prog1.other = prog2
prog2.other = prog1

Thread { prog1.process() }.start()
Thread { prog2.process() }.start()
