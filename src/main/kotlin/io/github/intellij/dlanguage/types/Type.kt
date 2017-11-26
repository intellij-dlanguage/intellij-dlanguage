package io.github.intellij.dlanguage.types

/**
 * Base class for all types
 */
abstract class Type {
    abstract fun typeToString(): String
    override fun toString(): String = typeToString()
}

class TypeConst(val ty: Type) : Type() {
    override fun typeToString(): String = "const($ty)"
}

open class TypeImmutable(val ty: Type) : Type() {
    override fun typeToString(): String = "immutable($ty)"
}
