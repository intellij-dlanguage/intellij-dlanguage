package io.github.intellij.dlanguage.psi.types

import io.github.intellij.dlanguage.utils.TypeSuffix

fun getDtypeWithSuffixes(elementType: DType, suffixes: List<TypeSuffix>): DType {
    var dtype = elementType
    for (suffix in suffixes) {
        if (suffix.oP_ASTERISK != null)
            dtype = DPointerType(dtype)
        else if (suffix.oP_BRACKET_LEFT != null) {
            dtype = if (suffix.type != null) {
                DAssociativeArrayType(
                    dtype,
                    suffix.type!!.dType
                )
            } else if (suffix.expressions.isEmpty()) {
                DArrayType(dtype, null)
            } else {
                DArrayType(dtype, null) // TODO use expression value to set length
            }
        } else if (suffix.oP_TRIPLEDOT != null) {
            dtype = DArrayType(dtype, null);
        } else if (suffix.kW_DELEGATE != null) {
            // TODO implement
        } else if (suffix.kW_FUNCTION != null) {
            // TODO implement
        }
    }
    return dtype
}
