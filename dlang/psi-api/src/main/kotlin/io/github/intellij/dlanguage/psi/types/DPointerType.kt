package io.github.intellij.dlanguage.psi.types

class DPointerType(val base: DType) : DType {

    override fun toString(): String = "$base*"
}
