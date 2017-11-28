package io.github.intellij.dlanguage.types

// Primitives
object DTypeVoid : DTypePrimitive("void")

object DTypeBool : DTypePrimitive("bool")
object DTypeByte : DTypePrimitive("byte")
object DTypeUByte : DTypePrimitive("ubyte")
object DTypeShort : DTypePrimitive("short")
object DTypeUShort : DTypePrimitive("ushort")
object DTypeInt : DTypePrimitive("int")
object DTypeUInt : DTypePrimitive("uint")
object DTypeLong : DTypePrimitive("long")
object DTypeULong : DTypePrimitive("ulong")
object DTypeCent : DTypePrimitive("cent")
object DTypeUCent : DTypePrimitive("ucent")
object DTypeFloat : DTypePrimitive("float")
object DTypeDouble : DTypePrimitive("double")
object DTypeReal : DTypePrimitive("real")
object DTypeIFloat : DTypePrimitive("ifloat")
object DTypeIDouble : DTypePrimitive("idouble")
object DTypeIReal : DTypePrimitive("ireal")
object DTypeCFloat : DTypePrimitive("cfloat")
object DTypeCDouble : DTypePrimitive("cdouble")
object DTypeCReal : DTypePrimitive("creal")
object DTypeChar : DTypePrimitive("char")
object DTypeWChar : DTypePrimitive("wchar")
object DTypeDChar : DTypePrimitive("dchar")

// Special
object DTypeSizeT : DTypePrimitive("size_t")

object DTypePtrDiffT : DTypePrimitive("ptrdiff_t")
object DTypeHashT : DTypePrimitive("hash_t")

// String
object DTypeString : DTypeArray(DTypeImmutable(DTypeChar)) {
    override fun typeToString(): String = "string"
}

object DTypeWString : DTypeArray(DTypeImmutable(DTypeWChar)) {
    override fun typeToString(): String = "wstring"
}

object DTypeDString : DTypeArray(DTypeImmutable(DTypeDChar)) {
    override fun typeToString(): String = "dstring"
}

object DTypeUnknown : DType() {
    override fun typeToString(): String = "<unknown>"
}
