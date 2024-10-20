package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.utils.ClassDeclaration
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
        // TODO note: better way to determine the type of the literal
        //   is to handle it at the lexer level (distinguish int from long based on the extension)
        if (integeR_LITERAL != null) {
            // TODO long, uint and ulong
            return DPrimitiveType.fromText("int")
        }
        if (floaT_LITERAL != null) {
            // TODO double, ifloat, idouble, real, ireal
            return DPrimitiveType.fromText("float")
        }
        if (characteR_LITERAL != null) {
            // TODO dchar and wchar
            return DPrimitiveType.fromText("char")
        }
        if (alternatE_WYSIWYG_STRINGs.isNotEmpty() || doublE_QUOTED_STRINGs.isNotEmpty() ||
            wysiwyG_STRINGs.isNotEmpty() || delimiteD_STRINGs.isNotEmpty() || tokeN_STRINGs.isNotEmpty()) {
            return DArrayType(DPrimitiveType.fromText("char"), null) // TODO immutable (and handle wstring and dstring)
        }
        return null
    }

    private fun getSuperType(aClass: ClassDeclaration): DType? {
        // TODO implement
        return null
    }
}
