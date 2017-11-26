package io.github.intellij.dlanguage.types

/**
 * Represent class type.
 *
 * @param name: class name
 * @param base: list of base types e.g.: class TypeName: BaseType1, BaseType2
 * @param isFinal: if class have final modifier
 */
class TypeClass(
    val name: String,
    val base: List<Type>,
    val isFinal: Boolean = false
) : Type() {
    override fun typeToString(): String = name
}
