package io.github.intellij.dlanguage.types

/**
 * For instance int* can be represent:
 * PointerType(PrimitiveType("int"))
 */
class DTypePointer(val ref: DType) : DType() {
    override fun typeToString(): String = "$ref*"
}
