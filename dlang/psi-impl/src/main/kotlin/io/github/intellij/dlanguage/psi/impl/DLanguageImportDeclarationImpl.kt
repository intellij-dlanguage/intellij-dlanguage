package io.github.intellij.dlanguage.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.interfaces.DVisibility.*
import io.github.intellij.dlanguage.psi.interfaces.DVisibilityStubKind
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.psi.named.*
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

    override fun getKW_STATIC(): PsiElement? =
        findChildByType(DlangTypes.KW_STATIC)

    override fun getKW_IMPORT(): PsiElement? =
        findChildByType(DlangTypes.KW_IMPORT)

    override fun getSingleImports(): List<DLanguageSingleImport> =
        PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageSingleImport::class.java)

    override fun getImportBindings(): DLanguageImportBindings? =
        PsiTreeUtil.getChildOfType(this, DLanguageImportBindings::class.java)

    override fun getOP_COMMAs(): List<PsiElement?> =
        findChildrenByType(DlangTypes.OP_COMMA)

    override fun getOP_SCOLON(): PsiElement? {
        return findChildByType(DlangTypes.OP_SCOLON)
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
        if (PsiScopesUtil.searchAttributeSpecifierInPreviousSiblings(this, processor, ResolveState.initial()) && processor.getVisibility() != null)
            return processor.getVisibility()!!

        val scope = PsiTreeUtil.getParentOfType(this, DLanguageFunctionDeclaration::class.java, UserDefinedType::class.java,
            DLanguageConstructor::class.java, DLanguageDestructor::class.java,
            DLanguageStaticConstructor::class.java, DLanguageStaticDestructor::class.java,
            DLanguageSharedStaticConstructor::class.java, DLanguageSharedStaticDestructor::class.java,
            DLanguageTemplateDeclaration::class.java, DLanguageTemplateMixinDeclaration::class.java,
            DLanguageBlockStatement::class.java)
        if (this.context != null && this.context !is PsiFile) {
            PsiScopesUtil.treeWalkUp(processor, this.context!!, scope)
        }
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
