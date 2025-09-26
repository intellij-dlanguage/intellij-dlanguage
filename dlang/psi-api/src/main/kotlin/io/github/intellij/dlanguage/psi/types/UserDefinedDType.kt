package io.github.intellij.dlanguage.psi.types

import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType

class UserDefinedDType(val name: String, private val instance: UserDefinedType) : DType {

    override fun toString(): String = name

    fun resolve(): UserDefinedType = instance
}
