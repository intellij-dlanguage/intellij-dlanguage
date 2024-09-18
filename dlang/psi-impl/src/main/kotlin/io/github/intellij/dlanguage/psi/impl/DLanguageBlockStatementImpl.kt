package io.github.intellij.dlanguage.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.tree.LazyParseablePsiElement
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageBlockStatement
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.interfaces.Statement
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl.processDeclarations

class DLanguageBlockStatementImpl(text: CharSequence?) : LazyParseablePsiElement(DlangTypes.BLOCK_STATEMENT, text), DLanguageBlockStatement {
    fun accept(visitor: DlangVisitor) {
        visitor.visitBlockStatement(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is DlangVisitor) {
            accept(visitor)
        } else {
            super.accept(visitor)
        }
    }

    override fun getStatements(): MutableList<Statement?> {
        return PsiTreeUtil.getChildrenOfTypeAsList<Statement?>(this, Statement::class.java)
    }

    override fun getOP_BRACES_RIGHT(): PsiElement? {
        return firstChild
    }

    override fun getOP_BRACES_LEFT(): PsiElement? {
        return lastChild
    }

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        return processDeclarations(this, processor, state, lastParent, place)
    }

    override fun toString(): String {
        return this.javaClass.simpleName + "(" + this.elementType + ")"
    }
}
