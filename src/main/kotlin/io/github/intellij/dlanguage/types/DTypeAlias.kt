package io.github.intellij.dlanguage.types

class DTypeAlias(val name: String, val alias: DType) : DType() {
    override fun typeToString(): String = alias.typeToString()
}
