package io.github.intellij.dlanguage.psi.types

class DPrimitiveType private constructor(val name: String) : DType {
    override fun toString(): String = name

    companion object {
        private val BOOL = DPrimitiveType("bool")
        private val BYTE = DPrimitiveType("byte")
        private val UBYTE = DPrimitiveType("ubyte")
        private val SHORT = DPrimitiveType("short")
        private val USHORT = DPrimitiveType("ushort")
        private val INT = DPrimitiveType("int")
        private val UINT = DPrimitiveType("uint")
        private val LONG = DPrimitiveType("long")
        private val ULONG = DPrimitiveType("ulong")
        private val FLOAT = DPrimitiveType("float")
        private val DOUBLE = DPrimitiveType("double")
        private val CHAR = DPrimitiveType("char")
        private val WCHAR = DPrimitiveType("wchar")
        private val DCHAR = DPrimitiveType("dchar")
        private val VOID = DPrimitiveType("void")
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
                else -> {
                    throw NotImplementedError("Missing implementation for type $text")
                }
            }
        }
    }

}
