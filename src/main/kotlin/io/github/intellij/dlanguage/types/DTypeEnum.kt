package io.github.intellij.dlanguage.types

class DTypeEnum(val name: String) : DType() {
    override fun typeToString(): String = name
}
