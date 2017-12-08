package io.github.intellij.dlanguage.types

open class DTypeArray(val base: DType, val size: Long? = null) : DType() {
    override fun typeToString(): String =
        if (size != null) "$base[$size]" else "$base[]"

    fun isFixedSize(): Boolean = size != null
}

class DTypeAssociativeArray(val base: DType, val key: String) : DType() {
    override fun typeToString(): String = "$base[$key]"
}
