package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiUtilCore
import io.github.intellij.dlanguage.DLanguage

open class DlangContext protected constructor() : TemplateContextType("D", "D Language") {

    override fun isInContext(context: TemplateActionContext): Boolean {
        return PsiUtilCore.getLanguageAtOffset(context.file, context.startOffset).isKindOf(DLanguage)
    }
}
