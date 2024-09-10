package io.github.intellij.dlanguage.codeinsight.linemarker

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration

class DubExecutableRunLineMarkerContributor : RunLineMarkerContributor() {
    override fun getInfo(element: PsiElement): Info? {
        if(!isDMainFunction(element)) return null

        val actions = ExecutorAction.getActions()
        return Info(
            AllIcons.RunConfigurations.TestState.Run,
            { psiElement -> actions.mapNotNull { getText(it, psiElement) }.joinToString("\n") },
            *actions
        )
    }

    private fun isDMainFunction(element: PsiElement) : Boolean {
        if (!(element.elementType == DlangTypes.ID && element.parent is DLanguageFunctionDeclaration
            && element.parent.parent is DlangPsiFile && element.text == "main"))
            return false
        val funcDecl = element.parent as DLanguageFunctionDeclaration
        // check return type
        if (!(funcDecl.isAuto ||
            listOf("void", "int", "noreturn").contains(funcDecl.basicType?.text)))
            return false
        if (funcDecl.parameters == null)
            return false
        if (funcDecl.parameters!!.parameters.size >= 2)
            return false

        val param = funcDecl.parameters!!.parameters.getOrNull(0) ?: return true
        return param.type?.basicType?.text == "string" && param.type?.typeSuffixs?.size == 1 && param.type?.typeSuffixs?.get(0)?.text == "[]"
    }
}
