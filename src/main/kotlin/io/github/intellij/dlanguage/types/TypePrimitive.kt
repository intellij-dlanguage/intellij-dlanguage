package io.github.intellij.dlanguage.types

abstract class TypePrimitive(val name: String) : Type() {
    override fun typeToString(): String = name
}
