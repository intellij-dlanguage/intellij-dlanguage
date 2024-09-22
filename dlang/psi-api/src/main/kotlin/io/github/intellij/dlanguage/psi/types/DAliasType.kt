package io.github.intellij.dlanguage.psi.types

class DAliasType(val name: String, val aliased: DType) : DType {
    override fun toString(): String = "alias $name = $aliased"
}
