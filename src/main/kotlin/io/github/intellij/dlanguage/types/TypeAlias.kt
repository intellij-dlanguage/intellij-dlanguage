package io.github.intellij.dlanguage.types

class TypeAlias(val name: String, val alias: Type) : Type() {
    override fun typeToString(): String = alias.typeToString()
}
