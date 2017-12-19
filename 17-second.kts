import java.util.*

var input = 369
var position = 0
var number = 0

(1 until 50_000_000).forEach {
    position = (position + input) % it + 1
    if(position == 1) {
        number = it
    }
}

println(number)
