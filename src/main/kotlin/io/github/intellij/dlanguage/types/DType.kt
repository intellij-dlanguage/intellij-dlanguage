package io.github.intellij.dlanguage.types

import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.utils.Expression


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
 * Represents modifiers on a type. E.g. const/immutable/shared etc.
 */
enum class TypeModifier {

}

/**
 * closely related to `io.github.intellij.dlanguage.types.TypeModifier`. Specifies ether a type is const/immutable/mutable
 */
enum class Mutability {

}

/**
 * Interface representing any type within D's type sysem
 */
interface Type {

    /**
     * determines wether are not two types can be implicitly converted. Should be the same as the
     * "implicitConvertibleTo" method in the compiler source.
     * Useful for implementing in IDE type checking and code completing parameters.
     */
    fun isImplicitylyConvertibleTo(to: Type): Boolean

    /**
     * Allows for access to what the valid properrties for any given type are. Useful for code completion.
     */
    abstract val properties: Set<TypeProperty>

    /**
     * allows for accessing any moodifiers theere may be on the given type.
     */
    abstract val modifiers: Set<TypeModifier>
    /**
     * see comment for the enum Mutability
     */
    abstract val mutability: Mutability

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
 * Same as serialize but in reverse. Will be needed for handling stub serialiazation of psi
 */
fun deserialize(inputStream: StubInputStream): Type {
    TODO("needs implmenting")
}


/**
 * function too determine the type of an arbritray expression
 */
fun deduceType(expr: Expression): Type {
    TODO("Needs implmentation.")
}
