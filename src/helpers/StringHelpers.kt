package helpers


fun String.letters(): List<String> {
    return this.split("").filter { it.isNotEmpty() }
}

fun String.setify(): Set<String> {
    var chars = mutableSetOf<String>()
    this.letters().forEach {
        var candidate = it
        var count = 1
        while (chars.contains(candidate)) {
            candidate = it + count
            count++
        }
        chars.add(candidate)
    }
    return chars
}