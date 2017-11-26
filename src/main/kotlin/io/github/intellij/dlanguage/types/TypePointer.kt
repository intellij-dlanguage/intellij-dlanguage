package io.github.intellij.dlanguage.types

/**
 * For instance int* can be represent:
 * PointerType(PrimitiveType("int"))
 */
class TypePointer(val ref: Type) : Type() {
    override fun typeToString(): String = "$ref*"
}
