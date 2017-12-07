package io.github.intellij.dlanguage.types

enum class Mutability(name: String? = null) {
    MUTABLE,
    CONST("const"),
    IMMUTABLE("immutable");

    val isMutable: Boolean get() = this == MUTABLE
}

enum class TypeModifier(name: String? = null) {
    CONST("const"),
    IMMUTABLE("immutable"),
    SHARED("shared")
}

/**
 * Base class for all types.
 * @param module: module where type is placed.
 */
abstract class DType(val module: String? = null) {
    val modifiers: MutableSet<TypeModifier> = HashSet()

    abstract fun typeToString(): String
    override fun toString(): String {
        var ty: String = typeToString()

        when {
            TypeModifier.IMMUTABLE in modifiers -> ty = "immutable($ty)"
            TypeModifier.CONST in modifiers -> ty = "const($ty)"
        }

        if (TypeModifier.SHARED in modifiers && TypeModifier.IMMUTABLE !in modifiers) {
            ty = "shared($ty)"
        }

        return ty
    }

    open fun implicitConversionTo(toType: DType) = DTypeUnknown

    fun toConst() : DType {
        modifiers.add(TypeModifier.CONST)
        return this
    }

    fun toImmutable() : DType {
        modifiers.add(TypeModifier.IMMUTABLE)
        return this
    }

    fun toShared() : DType {
        modifiers.add(TypeModifier.SHARED)
        return this
    }
}
