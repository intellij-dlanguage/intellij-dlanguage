package io.github.intellij.dlanguage.types

class TypeFunction(val returnType: Type, val argumentsTypes: List<Type>) : Type() {
    override fun typeToString(): String =
        "$returnType function(${argumentsTypes.joinToString(",") { it.typeToString() }})"
}

class TypeDelegate(val returnType: Type, val argumentsTypes: List<Type>) : Type() {
    override fun typeToString(): String =
        "$returnType delegate(${argumentsTypes.joinToString(",") { it.typeToString() }})"
}
