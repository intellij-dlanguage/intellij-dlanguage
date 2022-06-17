package io.github.intellij.dlanguage.codeinsight.linemarker

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes.IDENTIFIER
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration

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
        if (!(element.parent.elementType == IDENTIFIER && element.parent.parent is DlangFunctionDeclaration
            && element.parent.parent.parent.parent is DlangPsiFile && element.text == "main"))
            return false
        val funcDecl = element.parent.parent as DlangFunctionDeclaration
        // check return type
        if (!(funcDecl.isAuto || /*funcDecl.isNoreturn ||*/ // TODO enable when support of noreturn will be added
            listOf("void", "int").contains((element.parent.parent as DlangFunctionDeclaration).type?.type_2?.text)))
            return false
        if (!(funcDecl.parameters == null || funcDecl.parameters!!.parameters.size < 2))
            return false

        val param = funcDecl.parameters!!.parameters.getOrNull(1) ?: return true
        return param.type?.type_2?.text == "string" && param.type?.typeSuffixs?.size == 1 && param.type?.typeSuffixs?.get(1)?.text == "[]"
    }
}
