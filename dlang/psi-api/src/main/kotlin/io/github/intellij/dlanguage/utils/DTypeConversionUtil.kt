package io.github.intellij.dlanguage.utils

import io.github.intellij.dlanguage.psi.types.DAliasType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

fun getCommonType(type1: DType?, type2: DType?): DType? {
    type1?: return type2
    type2?: return type1
    val unboxedType1 = getUnaliasedType(type1)!!
    val unboxedType2 = getUnaliasedType(type2)!!

    if (isNumericType(unboxedType1) && isNumericType(unboxedType2))
        return getNumericTypeCommon(unboxedType1, unboxedType2)
    if (isCharType(unboxedType1) && isCharType(unboxedType2))
        return getCharTypeCommon(unboxedType1, unboxedType2)
    // TODO handle other cases, class common parent, pointers, ...
    return type1
}


fun isNumericType(type: DType): Boolean {
    return listOf(DPrimitiveType.DOUBLE, DPrimitiveType.FLOAT, DPrimitiveType.LONG,
        DPrimitiveType.ULONG, DPrimitiveType.INT, DPrimitiveType.UINT, DPrimitiveType.SHORT,
            DPrimitiveType.USHORT, DPrimitiveType.BYTE, DPrimitiveType.UBYTE).contains(type)
}

private fun getNumericTypeCommon(type1: DType, type2: DType): DType? {
    if (type1 === DPrimitiveType.DOUBLE || type2 === DPrimitiveType.DOUBLE) return DPrimitiveType.DOUBLE
    if (type1 === DPrimitiveType.FLOAT || type2 === DPrimitiveType.FLOAT) return DPrimitiveType.FLOAT
    if (type1 === DPrimitiveType.LONG || type2 === DPrimitiveType.LONG) return DPrimitiveType.LONG
    if (type1 === DPrimitiveType.ULONG || type2 === DPrimitiveType.ULONG) return DPrimitiveType.ULONG
    if (type1 === DPrimitiveType.INT || type2 === DPrimitiveType.INT) return DPrimitiveType.INT
    if (type1 === DPrimitiveType.UINT || type2 === DPrimitiveType.UINT) return DPrimitiveType.UINT
    if (type1 === DPrimitiveType.SHORT || type2 === DPrimitiveType.SHORT) return DPrimitiveType.SHORT
    if (type1 === DPrimitiveType.USHORT || type2 === DPrimitiveType.USHORT) return DPrimitiveType.USHORT
    if (type1 === DPrimitiveType.BYTE || type2 === DPrimitiveType.BYTE) return DPrimitiveType.BYTE
    if (type1 === DPrimitiveType.UBYTE || type2 === DPrimitiveType.UBYTE) return DPrimitiveType.UBYTE
    return null
}

private fun isCharType(type: DType): Boolean {
    return type === DPrimitiveType.CHAR || type === DPrimitiveType.WCHAR || type === DPrimitiveType.DCHAR
}

private fun getCharTypeCommon(type1: DType, type2: DType?): DType? {
    if (type1 === DPrimitiveType.UBYTE || type2 === DPrimitiveType.UBYTE) return DPrimitiveType.UBYTE
    if (type1 === DPrimitiveType.UBYTE || type2 === DPrimitiveType.UBYTE) return DPrimitiveType.UBYTE
    if (type1 === DPrimitiveType.UBYTE || type2 === DPrimitiveType.UBYTE) return DPrimitiveType.UBYTE
    return null
}

fun getUnaliasedType(type: DType?): DType? {
    var unboxedType = type?: return null
    while (unboxedType is DAliasType) {
        unboxedType = unboxedType.aliased
    }
    return unboxedType
}
