package io.github.intellij.dlanguage.types

/**
 * Base class for all types
 */
abstract class DType {
    abstract fun typeToString(): String
    override fun toString(): String = typeToString()
}

class DTypeConst(val ty: DType) : DType() {
    override fun typeToString(): String = "const($ty)"
}

open class DTypeImmutable(val ty: DType) : DType() {
    override fun typeToString(): String = "immutable($ty)"
}
