package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.CodeInsightUtil.findExpressionInRange
import com.intellij.featureStatistics.FeatureUsageTracker
import com.intellij.lang.surroundWith.SurroundDescriptor
import com.intellij.lang.surroundWith.Surrounder
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class DlangExpressionSurroundDescriptor : SurroundDescriptor {
    override fun getElementsToSurround(file: PsiFile, startOffset: Int, endOffset: Int): Array<out PsiElement> {
        val expr = findExpressionInRange(file, startOffset, endOffset) ?: return emptyArray()
        FeatureUsageTracker.getInstance().triggerFeatureUsed("codeassists.surroundwith.expression")
        return arrayOf(expr)
    }

    override fun getSurrounders(): Array<out Surrounder> = SURROUNDERS

    override fun isExclusive() = false

    companion object {
        private val SURROUNDERS = arrayOf(
            BlaSurrounder()
            /*DlangWithParenthesesSurrounder(),
            DlangWithNotSurrounder(),
            DlangWithIfExpSurrounder(),
            DlangWithWhileExpSurrounder()*/
        )
    }
}

class BlaSurrounder : Surrounder
{
    override fun getTemplateDescription(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surroundElements(project: Project, editor: Editor, elements: Array<out PsiElement>): TextRange? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isApplicable(elements: Array<out PsiElement>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
