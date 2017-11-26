package io.github.intellij.dlanguage.types

class TypeEnum(val name: String) : Type() {
    override fun typeToString(): String = name
}
