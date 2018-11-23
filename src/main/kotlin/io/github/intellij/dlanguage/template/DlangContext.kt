package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

open class DlangContext protected constructor() : TemplateContextType("D", "D Language") {

    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        // Todo: in never IDE's (2018.2.x) the suffix check does not work. does the baseLanguage check superseeds this?
        return (file.name.endsWith(".d")
            || file.viewProvider.baseLanguage == io.github.intellij.dlanguage.DLanguage)
    }
}
