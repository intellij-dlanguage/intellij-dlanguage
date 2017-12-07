package io.github.intellij.dlanguage.types

// Primitives
class DTypeVoid : DTypePrimitive("void")
class DTypeBool : DTypePrimitive("bool")
class DTypeByte : DTypePrimitive("byte")
class DTypeUByte : DTypePrimitive("ubyte")
class DTypeShort : DTypePrimitive("short")
class DTypeUShort : DTypePrimitive("ushort")
class DTypeInt : DTypePrimitive("int")
class DTypeUInt : DTypePrimitive("uint")
class DTypeLong : DTypePrimitive("long")
class DTypeULong : DTypePrimitive("ulong")
class DTypeCent : DTypePrimitive("cent")
class DTypeUCent : DTypePrimitive("ucent")
class DTypeFloat : DTypePrimitive("float")
class DTypeDouble : DTypePrimitive("double")
class DTypeReal : DTypePrimitive("real")
class DTypeIFloat : DTypePrimitive("ifloat")
class DTypeIDouble : DTypePrimitive("idouble")
class DTypeIReal : DTypePrimitive("ireal")
class DTypeCFloat : DTypePrimitive("cfloat")
class DTypeCDouble : DTypePrimitive("cdouble")
class DTypeCReal : DTypePrimitive("creal")
class DTypeChar : DTypePrimitive("char")
class DTypeWChar : DTypePrimitive("wchar")
class DTypeDChar : DTypePrimitive("dchar")

// Special
class DTypeSizeT : DTypePrimitive("size_t")

class DTypePtrDiffT : DTypePrimitive("ptrdiff_t")
class DTypeHashT : DTypePrimitive("hash_t")

// String
object DTypeString : DTypeArray(DTypeChar().toImmutable()) {
    override fun typeToString(): String = "string"
}

object DTypeWString : DTypeArray(DTypeWChar().toImmutable()) {
    override fun typeToString(): String = "wstring"
}

object DTypeDString : DTypeArray(DTypeDChar().toImmutable()) {
    override fun typeToString(): String = "dstring"
}

object DTypeUnknown : DType() {
    override fun typeToString(): String = "<unknown>"
}
