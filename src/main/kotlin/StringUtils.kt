internal fun String.toSnakeCase(): String {

    return replace(Regex("([a-z])([A-Z]+)"), "$1_$2").lowercase()
}