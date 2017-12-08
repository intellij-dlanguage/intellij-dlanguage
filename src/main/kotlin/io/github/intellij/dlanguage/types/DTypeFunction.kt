package io.github.intellij.dlanguage.types

class DTypeFunction(val returnType: DType, val argumentsTypes: List<DType>) : DType() {
    override fun typeToString(): String =
        "$returnType function(${argumentsTypes.joinToString(",") { it.typeToString() }})"
}

class DTypeDelegate(val returnType: DType, val argumentsTypes: List<DType>) : DType() {
    override fun typeToString(): String =
        "$returnType delegate(${argumentsTypes.joinToString(",") { it.typeToString() }})"
}
