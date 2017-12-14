package io.github.intellij.dlanguage.types

// Primitives
/**
 * some of these are platform specific.
 */
class DTypeVoid(override val sizeOf: Int = 0) : DTypePrimitive("void")

class DTypeBool(override val sizeOf: Int = 8) : DTypePrimitive("bool")
class DTypeByte(override val sizeOf: Int = 8) : DTypePrimitive("byte")
class DTypeUByte(override val sizeOf: Int = 8) : DTypePrimitive("ubyte")
class DTypeShort(override val sizeOf: Int = 16) : DTypePrimitive("short")
class DTypeUShort(override val sizeOf: Int = 16) : DTypePrimitive("ushort")
class DTypeInt(override val sizeOf: Int = 32) : DTypePrimitive("int")
class DTypeUInt(override val sizeOf: Int = 32) : DTypePrimitive("uint")
class DTypeLong(override val sizeOf: Int = 64) : DTypePrimitive("long")
class DTypeULong(override val sizeOf: Int = 64) : DTypePrimitive("ulong")
class DTypeCent : DTypePrimitive("cent")
class DTypeUCent : DTypePrimitive("ucent")
class DTypeFloat(override val sizeOf: Int = 32) : DTypePrimitive("float")
class DTypeDouble(override val sizeOf: Int = 64) : DTypePrimitive("double")
class DTypeReal(override val sizeOf: Int = 128) : DTypePrimitive("real")
class DTypeIFloat(override val sizeOf: Int = 32) : DTypePrimitive("ifloat")
class DTypeIDouble(override val sizeOf: Int = 64) : DTypePrimitive("idouble")
class DTypeIReal(override val sizeOf: Int = 128) : DTypePrimitive("ireal")
class DTypeCFloat(override val sizeOf: Int = 64) : DTypePrimitive("cfloat")
class DTypeCDouble(override val sizeOf: Int = 128) : DTypePrimitive("cdouble")
class DTypeCReal(override val sizeOf: Int = 256) : DTypePrimitive("creal")
class DTypeChar(override val sizeOf: Int = 8) : DTypePrimitive("char")
class DTypeWChar(override val sizeOf: Int = 16) : DTypePrimitive("wchar")
class DTypeDChar(override val sizeOf: Int = 32) : DTypePrimitive("dchar")

// Special

// All these are specified in terms of other types in object.d
//class DTypeHashT : DTypePrimitive("hash_t")
//class DTypeSizeT : DTypePrimitive("size_t")
//class DTypePtrDiffT : DTypePrimitive("ptrdiff_t")

// String

//object DTypeString : DTypeArray(DTypeChar().toImmutable()) {
//    override fun typeToString(): String = "string"
//}
//
//object DTypeWString : DTypeArray(DTypeWChar().toImmutable()) {
//    override fun typeToString(): String = "wstring"
//}
//
//object DTypeDString : DTypeArray(DTypeDChar().toImmutable()) {
//    override fun typeToString(): String = "dstring"
}

object DTypeUnknown : DType() {
    override fun typeToString(): String = "<unknown>"
}
