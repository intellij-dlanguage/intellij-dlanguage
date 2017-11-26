package io.github.intellij.dlanguage.types

open class TypeArray(val base: Type, val size: Long? = null) : Type() {
    override fun typeToString(): String =
        if (size != null) "$base[$size]" else "$base[]"

    fun isFixedSize(): Boolean = size != null
}

class TypeAssociativeArray(val base: Type, val key: String) : Type() {
    override fun typeToString(): String = "$base[$key]"
}
