package io.github.intellij.dlanguage.types

import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.utils.Expression

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

    fun toConst(): DType {
        modifiers.add(TypeModifier.CONST)
        return this
    }

    fun toImmutable(): DType {
        modifiers.add(TypeModifier.IMMUTABLE)
        return this
    }

    fun toShared(): DType {
        modifiers.add(TypeModifier.SHARED)
        return this
    }

    /**
     * Allows for access to what the valid properrties for any given type are. Useful for code completion.
     */
    abstract val properties: Set<TypeProperty>

    /**
     * specifies how much memory this type takes up. Will be useful in implementation if isImplicitlyConvertible
     */
    abstract val sizeOf: Int

    /**
     * serialize functionality. I anticipate that Types will neeed to be cached/acsess from psi, and therefore they will
     * need to be serialized to stubs
     */
    abstract fun serialize(outputStream: StubOutputStream)

}
    /**
 * represents a general property of a type. For example I could have the following type, "A":
 * class A{void func(){}}
 * The type A would have the property func, but would also have other properties, like .hashcode(), etc.
 * Another example would be the type "int". Int has the properties: .init, .max, etc.
 * A UFCS function declaration is not a property, since that is seperate from the actual type in question
 */
interface TypeProperty {


}




/**
 * Same as serialize but in reverse. Will be needed for handling stub serialiazation of psi
 */
fun deserialize(inputStream: StubInputStream): DType {
    TODO("needs implmenting")
}


/**
 * function too determine the type of an arbritray expression
 */
fun deduceType(expr: Expression): DType {
    TODO("Needs implmentation.")
}
