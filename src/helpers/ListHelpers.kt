package helpers

fun <E> MutableList<E>.append(other: E, times: Int) {
    var left = times
    while (left > 0) {
        this.add(other)
        left--
    }
}

fun <T> listWithPrefixAndSuffix(prefix: Int, suffix: Int, with: T, list: List<T>): List<T> {
    var newList = mutableListOf<T>()
    newList.append(with, prefix)
    newList.addAll(list)
    newList.append(with, suffix)
    return newList
}