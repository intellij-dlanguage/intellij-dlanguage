package io.github.intellij.dlanguage.types

import io.github.intellij.dlanguage.psi.DLanguageBuiltinType
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.ext.containsToken

abstract class DTypePrimitive(val name: String) : DType() {
    override fun typeToString(): String = name

    companion object {
        fun fromBuiltinPsiElement(psi: DLanguageBuiltinType): DType =
            when {
                psi.containsToken(DlangTypes.KW_BYTE) -> DTypeByte()
                psi.containsToken(DlangTypes.KW_VOID) -> DTypeVoid()
                psi.containsToken(DlangTypes.KW_BOOL) -> DTypeBool()
                psi.containsToken(DlangTypes.KW_BYTE) -> DTypeByte()
                psi.containsToken(DlangTypes.KW_UBYTE) -> DTypeUByte()
                psi.containsToken(DlangTypes.KW_SHORT) -> DTypeShort()
                psi.containsToken(DlangTypes.KW_USHORT) -> DTypeUShort()
                psi.containsToken(DlangTypes.KW_INT) -> DTypeInt()
                psi.containsToken(DlangTypes.KW_UINT) -> DTypeUInt()
                psi.containsToken(DlangTypes.KW_LONG) -> DTypeLong()
                psi.containsToken(DlangTypes.KW_ULONG) -> DTypeULong()
                psi.containsToken(DlangTypes.KW_CENT) -> DTypeCent()
                psi.containsToken(DlangTypes.KW_UCENT) -> DTypeUCent()
                psi.containsToken(DlangTypes.KW_FLOAT) -> DTypeFloat()
                psi.containsToken(DlangTypes.KW_DOUBLE) -> DTypeDouble()
                psi.containsToken(DlangTypes.KW_REAL) -> DTypeReal()
                psi.containsToken(DlangTypes.KW_IFLOAT) -> DTypeIFloat()
                psi.containsToken(DlangTypes.KW_IDOUBLE) -> DTypeIDouble()
                psi.containsToken(DlangTypes.KW_IREAL) -> DTypeIReal()
                psi.containsToken(DlangTypes.KW_CFLOAT) -> DTypeCFloat()
                psi.containsToken(DlangTypes.KW_CDOUBLE) -> DTypeCDouble()
                psi.containsToken(DlangTypes.KW_CREAL) -> DTypeCReal()
                psi.containsToken(DlangTypes.KW_CHAR) -> DTypeChar()
                psi.containsToken(DlangTypes.KW_WCHAR) -> DTypeWChar()
                psi.containsToken(DlangTypes.KW_DCHAR) -> DTypeDChar()
                else -> DTypeUnknown
            }
    }
}
