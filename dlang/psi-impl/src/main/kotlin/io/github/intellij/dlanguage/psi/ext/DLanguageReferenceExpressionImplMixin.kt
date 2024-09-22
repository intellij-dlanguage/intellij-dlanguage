package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageReferenceExpression
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement
import io.github.intellij.dlanguage.psi.references.GenericExpressionElementReference
import io.github.intellij.dlanguage.psi.types.DAliasType
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DAssociativeArrayType
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType
import io.github.intellij.dlanguage.psi.types.UserDefinedDType
import io.github.intellij.dlanguage.utils.EnumDeclaration
import io.github.intellij.dlanguage.utils.FunctionDeclaration

abstract class DLanguageReferenceExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageReferenceExpression {

    override fun getDType(): DType? {
        val result = reference?.resolve()
        if (result is DTypedElement) {
            return result.dType
        }
        if (result is FunctionDeclaration) {
            return result.returnDType
        }
        val qualifierType = expression?.dType
        // Compiler defined property? : https://dlang.org/spec/property.html
        val dtype =  when (identifier?.text) {
            "init" -> qualifierType
            "alignof",
            "sizeof" -> DPrimitiveType.fromText("uint") // TODO actually it is a size_t
            "mangleof",
            "stringof" -> DArrayType(DPrimitiveType.fromText("char"), null) // TODO it is immutable(char) array
            else -> null
        }
        if (dtype != null) {
            return dtype
        }
        val arrayType = getArrayType(qualifierType)
        val assocArrayType = getAssociativeArrayType(qualifierType)
        if (arrayType != null) {
            return when (identifier?.text) {
                "dup" -> arrayType
                "idup" -> arrayType // TODO idup return an immutable array
                "length" -> DPrimitiveType.fromText("int") // TODO size_t
                "ptr" -> DPointerType(arrayType)
                "tupleof" -> if (arrayType.isFixedSize()) DUnknownType() else null // TODO replace DUnknownType by tuple
                "capacity" -> if (arrayType.isFixedSize()) null else DPrimitiveType.fromText("int") // TODO size_t
                else -> null
            }
        } else if (assocArrayType != null) {
            return when (identifier?.text) {
                "length" -> DPrimitiveType.fromText("int") // TODO size_t
                "dup" -> assocArrayType
                "keys" -> DArrayType(assocArrayType.keyType, null)
                "values" -> DArrayType(assocArrayType.valueType, null)
                "rehash" -> assocArrayType
                "clear" -> DPrimitiveType.fromText("void")
                "byKey" -> DUnknownType() // TODO
                "byValue" -> DUnknownType() // TODO
                "byKeyValue" -> DUnknownType() // TODO
                // TODO get, require and update which are functions
                else -> null
            }
        } else if (isEnumType(qualifierType)) {
            return when (identifier?.text) {
                "init" -> qualifierType
                "min",
                "max" -> DPrimitiveType.fromText("int") // TODO actually need to fetch the real type from the enum
                else -> null
            }
        }
        // TODO handle all compiler defined properties (class, struct, vector)

        return null
    }

    private fun getArrayType(type: DType?): DArrayType? {
        if (type is DPointerType) return getArrayType(type.base)
        if (type is DAliasType) return  getArrayType(type.aliased)
        return type as? DArrayType
    }

    private fun getAssociativeArrayType(type: DType?): DAssociativeArrayType? {
        if (type is DPointerType) return getAssociativeArrayType(type.base)
        if (type is DAliasType) return  getAssociativeArrayType(type.aliased)
        return type as? DAssociativeArrayType
    }

    private fun isEnumType(type: DType?): Boolean {
        if (type is DPointerType) return isEnumType(type.base)
        if (type is DAliasType) return  isEnumType(type.aliased)
        return (type is UserDefinedDType && type.resolve() is EnumDeclaration)
    }

    override fun getReference(): PsiReference? {
        val referenceElement: PsiElement
        val range: TextRange
        if (identifier != null) {
            referenceElement = identifier!!
            range = referenceElement.textRangeInParent
        } else if (templateInstance != null && templateInstance!!.identifier != null) {
            referenceElement = templateInstance!!.identifier!!
            range = referenceElement.textRangeInParent.shiftRight(templateInstance!!.startOffsetInParent)
        }
        else {
            return null
        }
        return GenericExpressionElementReference(this, range, expression, referenceElement.text)
    }
}
