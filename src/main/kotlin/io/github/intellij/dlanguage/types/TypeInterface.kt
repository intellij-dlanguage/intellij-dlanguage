package io.github.intellij.dlanguage.types

/**
 * Represent class type.
 *
 * @param name: class name
 * @param base: list of base types e.g.: class TypeName: BaseType1, BaseType2
 */
class TypeInterface(
    val name: String,
    val base: List<Type>
) : Type() {
    override fun typeToString(): String = name
}
