package io.github.intellij.dlanguage.types

// Primitives
object TypeVoid : TypePrimitive("void")

object TypeBool : TypePrimitive("bool")
object TypeByte : TypePrimitive("byte")
object TypeUByte : TypePrimitive("ubyte")
object TypeShort : TypePrimitive("short")
object TypeUShort : TypePrimitive("ushort")
object TypeInt : TypePrimitive("int")
object TypeUInt : TypePrimitive("uint")
object TypeLong : TypePrimitive("long")
object TypeULong : TypePrimitive("ulong")
object TypeCent : TypePrimitive("cent")
object TypeUCent : TypePrimitive("ucent")
object TypeFloat : TypePrimitive("float")
object TypeDouble : TypePrimitive("double")
object TypeReal : TypePrimitive("real")
object TypeIFloat : TypePrimitive("ifloat")
object TypeIDouble : TypePrimitive("idouble")
object TypeIReal : TypePrimitive("ireal")
object TypeCFloat : TypePrimitive("cfloat")
object TypeCDouble : TypePrimitive("cdouble")
object TypeCReal : TypePrimitive("creal")
object TypeChar : TypePrimitive("char")
object TypeWChar : TypePrimitive("wchar")
object TypeDChar : TypePrimitive("dchar")

// Special
object TypeSizeT : TypePrimitive("size_t")

object TypePtrDiffT : TypePrimitive("ptrdiff_t")
object TypeHashT : TypePrimitive("hash_t")

// String
object TypeString : TypeArray(TypeImmutable(TypeChar)) {
    override fun typeToString(): String = "string"
}

object TypeWString : TypeArray(TypeImmutable(TypeWChar)) {
    override fun typeToString(): String = "wstring"
}

object TypeDString : TypeArray(TypeImmutable(TypeDChar)) {
    override fun typeToString(): String = "dstring"
}

object TypeUnknown : Type() {
    override fun typeToString(): String = "<unknown>"
}
