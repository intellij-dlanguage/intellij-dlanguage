package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.utils.ClassDeclaration
import io.github.intellij.dlanguage.utils.DPsiLiteralUtil
import io.github.intellij.dlanguage.utils.LiteralExpression

abstract class DLanguageLiteralExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    LiteralExpression {

    override fun getDType(): DType? {
        if (kW_THIS != null) {
            var scope = context
            while (scope != null) {
                if (scope is UserDefinedType)
                    return scope.dType
                scope = scope.context
            }
            return null
        }
        if (kW_SUPER != null) {
            var scope = context
            while (scope != null) {
                if (scope is ClassDeclaration)
                    return getSuperType(scope)
                scope = scope.context
            }
            return null
        }
        if (kW_NULL != null) {
            return DPrimitiveType.fromText("null")
        }
        if (kW_TRUE != null || kW_FALSE != null) {
            return DPrimitiveType.fromText("bool")
        }
        if (integeR_LITERAL != null) {
            val text = integeR_LITERAL!!.text
            val intValue = getLongValue(text)
            if (text.endsWith("uL") || text.endsWith("UL") || text.endsWith("Lu") || text.endsWith("LU")) {
                return DPrimitiveType.fromText("ulong")
            } else if (text.endsWith("u") || text.endsWith("U")) {
                if (intValue <= 4_294_967_295U)
                    return DPrimitiveType.fromText("uint")
                return DPrimitiveType.fromText("ulong")
            } else if (text.endsWith("L")) {
                if ((DPsiLiteralUtil.isHexadecimal(text) || DPsiLiteralUtil.isBinary(text)) && intValue > DPsiLiteralUtil.SIGNED_LONG_MAX.toULong())
                    return DPrimitiveType.fromText("ulong")
                // if a regular long (not binary not hex) is declared a higher limit than 9_223_372_036_854_775_807L,
                // then it’s an overflow, an error should be reported
                return DPrimitiveType.fromText("long")
            } else {
                // no extension
                if (intValue <= 2_147_483_647U)
                    return  DPrimitiveType.fromText("int")
                // binary and hexadecimal value can be uint even without a suffix
                if ((DPsiLiteralUtil.isHexadecimal(text) || DPsiLiteralUtil.isBinary(text)) && intValue <= 4_294_967_295U) {
                    return DPrimitiveType.fromText("uint")
                }
                if (intValue <= DPsiLiteralUtil.SIGNED_LONG_MAX.toULong())
                    return DPrimitiveType.fromText("long")
                return DPrimitiveType.fromText("ulong")
            }
        }
        if (floaT_LITERAL != null) {
            val last = floaT_LITERAL!!.text.last()
            if (last == 'i') {
                val preLast = floaT_LITERAL!!.text[floaT_LITERAL!!.text.lastIndex - 1]
                return when (preLast) {
                    'f', 'F' -> DPrimitiveType.fromText("ifloat")
                    'L' -> DPrimitiveType.fromText("ireal")
                    else -> DPrimitiveType.fromText("idouble")
                }
            }
            return when (last) {
                'f', 'F' -> DPrimitiveType.fromText("float")
                'L' -> DPrimitiveType.fromText("real")
                else -> DPrimitiveType.fromText("double")
            }
        }
        if (characteR_LITERAL != null) {
            // TODO dchar and wchar (need to check the char value to determine his real type)
            return DPrimitiveType.fromText("char")
        }
        if (alternatE_WYSIWYG_STRINGs.isNotEmpty() || doublE_QUOTED_STRINGs.isNotEmpty() ||
            wysiwyG_STRINGs.isNotEmpty() || delimiteD_STRINGs.isNotEmpty() || tokeN_STRINGs.isNotEmpty() ||
            heX_STRINGs.isNotEmpty()) {
            return DArrayType(DPrimitiveType.fromText("char"), null) // TODO immutable (and handle wstring and dstring)
        }
        return null
    }

    private fun getLongValue(text: String): ULong {
        var finalText = text
        // remove the suffix, can loop at most twice
        while (finalText.endsWith("U") || finalText.endsWith("u") || finalText.endsWith("L"))
            finalText = finalText.substring(0, finalText.length - 1)
        finalText = finalText.replace("_", "")

        if (DPsiLiteralUtil.isBinary(finalText)) {
            finalText = finalText.substring(2)
            return try {
                finalText.toULong(2)
            } catch (_: NumberFormatException) {
                // Number too high probably
                ULong.MAX_VALUE
            }
        }
        if (DPsiLiteralUtil.isHexadecimal(finalText)) {
            finalText = finalText.substring(2)
            return try {
                finalText.toULong(16)
            } catch (_: NumberFormatException) {
                // Number too high probably
                ULong.MAX_VALUE
            }
        }
        return try {
            finalText.toULong()
        } catch (_: java.lang.NumberFormatException) {
            // Number too high probably
            ULong.MAX_VALUE
        }
    }

    private fun getSuperType(aClass: ClassDeclaration): DType? {
        return aClass.parentClass?.dType
    }
}
