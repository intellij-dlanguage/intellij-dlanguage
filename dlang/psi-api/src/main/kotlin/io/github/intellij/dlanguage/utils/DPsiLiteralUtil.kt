package io.github.intellij.dlanguage.utils

object DPsiLiteralUtil {

    @JvmStatic
    val SIGNED_LONG_MAX: Long = 9_223_372_036_854_775_807L

    fun isHexadecimal(text: String): Boolean = text.startsWith("0x") || text.startsWith("0X")

    fun isBinary(text: String): Boolean = text.startsWith("0b") || text.startsWith("0B")

    fun isDeclaredUnsignedLong(text: String): Boolean = text.endsWith("UL") || text.endsWith("uL") || text.endsWith("LU") || text.endsWith("Lu")

    fun hasLongNotUnsignedSuffix(text: String): Boolean = !isDeclaredUnsignedLong(text) && (text.endsWith("L") || text.endsWith("l"))

    fun parseIntegerLiteral(text: String): ULong? {
        var finalText = text
        // remove the suffix, can loop at most twice
        while (finalText.endsWith("U") || finalText.endsWith("u") || finalText.endsWith("L"))
            finalText = finalText.substring(0, finalText.length - 1)
        finalText = finalText.replace("_", "")

        if (isBinary(finalText)) {
            finalText = finalText.substring(2)
            return try {
                finalText.toULong(2)
            } catch (_: NumberFormatException) {
                // Number too high probably
                null
            }
        }
        if (isHexadecimal(finalText)) {
            finalText = finalText.substring(2)
            return try {
                finalText.toULong(16)
            } catch (_: NumberFormatException) {
                // Number too high probably
                null
            }
        }
        return try {
            finalText.toULong()
        } catch (_: java.lang.NumberFormatException) {
            // Number too high probably
            null
        }
    }
}
