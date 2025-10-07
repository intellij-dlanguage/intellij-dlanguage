package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageArrayLiteralExpression
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DAssociativeArrayType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType
import io.github.intellij.dlanguage.utils.getCommonType

abstract class DLanguageArrayLiteralExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageArrayLiteralExpression {

    override fun getDType(): DType? {
        if (assocArrayLiteral != null) {
            var keyType: DType? = null
            var valueType: DType? = null
            for (keyValue in assocArrayLiteral!!.keyValuePairs!!.keyValuePairs) {
                if (keyValue.expressions.isEmpty())
                    continue
                keyType = getCommonType(keyType, keyValue.expressions.first().dType)
                if (keyValue.expressions.size > 1)
                    valueType = getCommonType(valueType, keyValue.expressions[1].dType)
            }
            return DAssociativeArrayType(valueType?: DUnknownType(), keyType?: DUnknownType())
        }
        assert (arrayLiteral != null)
        var commonType: DType? = null
        for (expr in arrayLiteral!!.expressions) {
            commonType = getCommonType(commonType, expr.dType)
        }
        return DArrayType(commonType?: DUnknownType(), arrayLiteral!!.expressions.size.toLong())
    }
}
