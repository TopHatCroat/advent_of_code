import com.sun.xml.internal.fastinfoset.util.StringArray
import java.io.File
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Matcher
import kotlin.collections.ArrayList

data class Node(var name: String = "", var weight: Int = 0, var deps: MutableList<String> = LinkedList()) {
    private val regex = Regex("(\\w*) \\((\\d*)\\)( -> (.*))?")

    constructor(string: String) : this() {
        regex.find(string)?.groupValues?.let {
            name = it[1]
            weight = it[2].toInt()
            deps = if (it[4].isEmpty())
                ArrayList()
            else
                it[4].split(",").map { it.replace(" ", "") }.toMutableList()
        }
    }
}

data class Tree(var element: Node, var children: MutableList<Tree> = ArrayList())

var input = File("inputs/07.txt").readLines(charset("UTF-8"))
        .map { Node(it) } as MutableList

lateinit var root: Tree
input.forEach { node ->
    if (input.none { it.deps.contains(node.name) }) {
        root = Tree(node)
        return@forEach
    }
}

populate(root)

weightAndFix(root)


fun populate(tree: Tree) {
    if (tree.element.deps.isEmpty())
        return

    tree.element.deps.forEach { dep ->
        var n = Tree(findNode(dep))
        populate(n)
        tree.children.add(n)
    }
}

fun weightAndFix(tree: Tree): Int {
    if (tree.children.isEmpty())
        return tree.element.weight

    var weights = ArrayList<Int>()
    tree.children.forEach {
        weights.add(weightAndFix(it))
    }

    if (weights.min() != weights.max()) {
        (0 until tree.children.size).forEach {
            println(" ${weights[it]} ${tree.element}")
            tree.children[it].children.forEach { println("   ${it.element}") }
        }
    }

    return weights.fold(tree.element.weight) { i, j -> i + j }
}

fun findNode(dep: String) = input.first { node ->
    node.name == dep
}
