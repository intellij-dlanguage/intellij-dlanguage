package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.quickfix.FormatNumber

/**
 * todo 100_000L is incorrectly flagged as wrong
 */
class FormatNumbersForReadability :LocalInspectionTool(){
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = FormatNumbersForReadabilityVisitor(holder)
    override fun getDescriptionFileName(): String = "NumbersReadability.html"
    override fun getDisplayName(): String = "Numbers Readability"
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class FormatNumbersForReadabilityVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    override fun visitElement(element: PsiElement) {
        val text = element.text
        val length = text.length
        if(isNumber(element) && length > 3){
            if(!isCorrectlyFormatted(text)){
                holder.registerProblem(element,"Number not formatted correctly", FormatNumber(element))
            }
        }
    }
    fun isCorrectlyFormatted(text: String): Boolean{
        val length = text.length
        val locationOfUnderScores = length - 4 downTo 0 step 4
        for (i in locationOfUnderScores){
            if(text[i] != '_') return false
        }
        return true

    }
    fun isNumber(element: PsiElement): Boolean {
        if(element !is LeafPsiElement) return false
        return element.elementType == DlangTypes.INTEGER_LITERAL
    }
}
