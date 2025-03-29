package io.github.intellij.dlanguage.psi.types

class DPrimitiveType private constructor(val name: String) : DType {
    override fun toString(): String = name

    companion object {
        val BOOL = DPrimitiveType("bool")
        val BYTE = DPrimitiveType("byte")
        val UBYTE = DPrimitiveType("ubyte")
        val SHORT = DPrimitiveType("short")
        val USHORT = DPrimitiveType("ushort")
        val INT = DPrimitiveType("int")
        val UINT = DPrimitiveType("uint")
        val LONG = DPrimitiveType("long")
        val ULONG = DPrimitiveType("ulong")
        val FLOAT = DPrimitiveType("float")
        val DOUBLE = DPrimitiveType("double")
        val CHAR = DPrimitiveType("char")
        val WCHAR = DPrimitiveType("wchar")
        val DCHAR = DPrimitiveType("dchar")
        val VOID = DPrimitiveType("void")

        val REAL = DPrimitiveType("ireal")
        val CENT = DPrimitiveType("cent")
        val UCENT = DPrimitiveType("ucent")
        val CFLOAT = DPrimitiveType("cfloat")
        val CDOUBLE = DPrimitiveType("cdouble")
        val CREAL = DPrimitiveType("creal")
        val IFLOAT = DPrimitiveType("ifloat")
        val IDOUBLE = DPrimitiveType("idouble")
        val IREAL = DPrimitiveType("ireal")

        // not existing actually but represent a null pointer
        private val NULL = DPrimitiveType("null")

        @JvmStatic
        fun fromText(text: String): DPrimitiveType {
            return when (text) {
                "bool" -> BOOL
                "byte" -> BYTE
                "ubyte" -> UBYTE
                "short" -> SHORT
                "ushort" -> USHORT
                "int" -> INT
                "uint" -> UINT
                "long" -> LONG
                "ulong" -> ULONG
                "float" -> FLOAT
                "double" -> DOUBLE
                "char" -> CHAR
                "wchar" -> WCHAR
                "dchar" -> DCHAR
                "void" -> VOID
                "null" -> NULL

                "real" -> REAL
                "cent" -> CENT
                "ucent" -> UCENT
                "cfloat" -> CFLOAT
                "cdouble" -> CDOUBLE
                "ifloat" -> IFLOAT
                "idouble" -> IDOUBLE
                "creal" -> CREAL
                "ireal" -> IREAL

                else -> {
                    throw NotImplementedError("Missing implementation for type $text")
                }
            }
        }
    }

}
