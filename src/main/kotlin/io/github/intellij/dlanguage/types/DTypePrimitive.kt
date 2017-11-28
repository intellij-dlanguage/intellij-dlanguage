package io.github.intellij.dlanguage.types

abstract class DTypePrimitive(val name: String) : DType() {
    override fun typeToString(): String = name
}
