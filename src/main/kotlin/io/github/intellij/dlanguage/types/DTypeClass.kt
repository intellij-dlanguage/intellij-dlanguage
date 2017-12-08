package io.github.intellij.dlanguage.types

/**
 * Represent class type.
 *
 * @param name: class name
 * @param base: list of base types e.g.: class TypeName: BaseType1, BaseType2
 * @param isFinal: if class have `final` modifier
 * @param isStatic: if class have `static` modifier
 */
class DTypeClass(
    val name: String,
    val base: List<DType>,
    val isFinal: Boolean = false,
    val isStatic: Boolean = false
) : DType() {
    override fun typeToString(): String = name
}
