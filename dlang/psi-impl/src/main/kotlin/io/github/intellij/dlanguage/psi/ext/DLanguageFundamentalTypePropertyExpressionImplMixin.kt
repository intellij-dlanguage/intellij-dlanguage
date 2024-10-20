package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageFundamentalTypePropertyExpression
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageFundamentalTypePropertyExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageFundamentalTypePropertyExpression {
    override fun getDType(): DType? {
        identifier?:return null
        // TODO need to add a reference to be able to resolve static fields
        val result = reference?.resolve()
        // matched something, means it is a static field
        if (result is DTypedElement) {
            return result.dType
        }
        val qualifierType = basicType?.dType
        return when (identifier?.text) {
            "init" -> qualifierType
            "alignof",
            "sizeof" -> DPrimitiveType.fromText("uint") // TODO actually it is a size_t
            "mangleof",
            "stringof" -> DArrayType(DPrimitiveType.fromText("char"), null) // TODO it is immutable(char) array
            "min",
            "max" -> if (isIntOrFloatType(qualifierType)) qualifierType else null
            "infinity",
            "nan",
            "epsilon",
            "min_normal" -> if (isFloatType(qualifierType)) qualifierType else null
            "dig",
            "mant_dig",
            "max_10_exp",
            "max_exp",
            "min_10_exp",
            "min_exp"  -> DPrimitiveType.fromText("int")
            // TODO others (specs are not clear enough)
            else -> null
        }
    }

    private fun isIntOrFloatType(type: DType?): Boolean {
        return setOf("byte", "ubyte", "short", "ushort", "int", "uint",
            "long", "ulong", "float", "double").contains((type as? DPrimitiveType)?.name)
    }

    private fun isFloatType(type: DType?): Boolean {
        return setOf("float", "double").contains((type as? DPrimitiveType)?.name)
    }
}
