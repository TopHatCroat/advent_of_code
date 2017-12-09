import com.sun.xml.internal.fastinfoset.util.StringArray
import java.io.File
import java.nio.charset.Charset
import java.util.regex.Matcher


data class Node(var name: String = "", var weight: Int = 0, var deps: Array<String> = Array(0, { _ -> "" })) {
    private val regex = Regex("(\\w*) \\((\\d*)\\)( -> (.*))?")

    constructor(string: String) : this() {
        regex.find(string)?.groupValues?.let {
            name = it[1]
            weight = it[2].toInt()
            deps = it[4].split(", ").toTypedArray()
        }
    }
}

var input = File("inputs/07.txt").readLines(charset("UTF-8"))
        .map { Node(it) }
        .filter { it.deps.isNotEmpty() }

input.forEach { node ->
    if(input.none { it.deps.contains(node.name) }) {
        print(node)
        return@forEach
    }
}
