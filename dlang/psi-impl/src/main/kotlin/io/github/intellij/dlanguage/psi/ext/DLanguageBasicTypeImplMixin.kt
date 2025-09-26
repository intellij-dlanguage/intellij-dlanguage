package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageBasicType
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageModule
import io.github.intellij.dlanguage.psi.named.DLanguagePackage
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType
import io.github.intellij.dlanguage.utils.NamedImportBind

abstract class DLanguageBasicTypeImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageBasicType {

    override fun getDType(): DType {
        if (builtinType != null) {
            return DPrimitiveType.fromText(builtinType!!.text);
        }
        if (qualifiedIdentifier != null) {
            var resolved = qualifiedIdentifier?.reference?.resolve()
            var dType: DType = DUnknownType()
            if (resolved != null) {
                dType = when (resolved) {
                    is DTypedElement -> resolved.dType
                    is DLanguageFunctionDeclaration -> resolved.returnDType
                    is NamedImportBind -> DUnknownType() // TODO implement (needs to put the implementation int the NamedImportBind)
                    is DLanguagePackage -> DUnknownType()
                    is DLanguageModule -> DUnknownType()
                    else -> {
                        assert(false) {"Unexpected/Unimplemented case of Qualified identifier DType for a Basic Type"}
                        DUnknownType()
                    }
                }
            }
            if (qualifiedIdentifier!!.oP_BRACKET_LEFT != null) {
                return DArrayType(dType, null) // TODO set size
            }
            return dType
        }
        if (type != null) {
            var dtype = type!!.dType
            // TODO theses are type constraints, they enforce different properties on the values of this types
            if (kW_CONST != null) {

            }
            if (kW_IMMUTABLE != null) {

            }
            if (kW_INOUT != null) {

            }
            if (kW_SHARED != null) {

            }
            return dtype
        }
        // Fallback case
        return DUnknownType()
    }
}
