package io.github.intellij.dlanguage.utils


/**
 * Take the opening delimiter of a String and return the corresponding closing one
 */
fun getCorrespondingClosingDelimiter(openingDelimiter: String) : String {
    return when (openingDelimiter) {
        "(" -> ")"
        "[" -> "]"
        "{" -> "}"
        "<" -> ">"
        else -> openingDelimiter
    }
}

/**
 * Take a Delimited String content without the surrounding delimiters (`q"` and `"`) and
 * return the opening delimiter, or null if the delimiter is invalid.
 */
fun getOpeningDelimiter(literal: String) : String? {
    if (listOf('(', ')' , '[', ']', '{', '}', '<', '>', '/', '\\').indexOf(literal[0]) != -1)
        return literal[0].toString()

    // Invalid delimiter
    if (literal[0].isWhitespace())
        return null

    // otherwise it’s a id\n
    val lines = literal.lines()
    if (lines.size == 1)
        return null // invalid string delimiter
    return lines[0]
}
