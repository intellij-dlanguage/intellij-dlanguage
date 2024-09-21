package io.github.intellij.dlanguage.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageAttribute
import io.github.intellij.dlanguage.psi.DLanguageImportBindings
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.interfaces.DVisibility.*
import io.github.intellij.dlanguage.psi.interfaces.DVisibilityStubKind
import io.github.intellij.dlanguage.psi.named.DLanguageSingleImport
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.psi.scope.processor.DVisibilityProcessor
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl.processDeclarations
import io.github.intellij.dlanguage.stubs.DLanguageImportDeclarationStub

class DLanguageImportDeclarationImpl : DStubBasedPsiElementBase<DLanguageImportDeclarationStub>,
    DLanguageImportDeclaration {
    constructor(stub: DLanguageImportDeclarationStub,
                elementType: IStubElementType<*, *>) : super(stub, elementType)

    constructor(node: ASTNode) : super(node)

    fun accept(visitor: DlangVisitor) {
        visitor.visitImportDeclaration(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is DlangVisitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }

    override fun getKW_IMPORT(): PsiElement? {
        return findChildByType<PsiElement?>(DlangTypes.KW_IMPORT)
    }

    override fun getSingleImports(): MutableList<DLanguageSingleImport> {
        return PsiTreeUtil.getChildrenOfTypeAsList<DLanguageSingleImport>(this, DLanguageSingleImport::class.java)
    }

    override fun getImportBindings(): DLanguageImportBindings? {
        return PsiTreeUtil.getChildOfType<DLanguageImportBindings?>(this, DLanguageImportBindings::class.java)
    }

    override fun getOP_COMMAs(): MutableList<PsiElement?> {
        return findChildrenByType<PsiElement?>(DlangTypes.OP_COMMA)
    }

    override fun getOP_SCOLON(): PsiElement? {
        return findChildByType<PsiElement?>(DlangTypes.OP_SCOLON)
    }

    override fun visibility(): DVisibility {
        if (greenStub != null) {
            return when (greenStub!!.visibility) {
                DVisibilityStubKind.EXPORT -> Export
                DVisibilityStubKind.PUBLIC -> Public
                DVisibilityStubKind.PRIVATE -> Private
                DVisibilityStubKind.PROTECTED -> Protected
                DVisibilityStubKind.PACKAGE -> Package(null)
                DVisibilityStubKind.PACKAGE_SPECIFIC -> Package(greenStub!!.packageVisibilityName)
            }
        }

        val attributes = findChildrenByType<DLanguageAttribute>(DlangTypes.ATTRIBUTE)
        for (attribute in attributes) {
            if (attribute.kW_PUBLIC != null)
                return Public
        }
        val processor = DVisibilityProcessor()
        PsiScopesUtil.walkChildrenScopes(this.context?:this.containingFile, processor, ResolveState.initial(), null, this)
        return processor.getVisibility() ?: Private
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        return processDeclarations(this, processor, state, lastParent, place)
    }
}
