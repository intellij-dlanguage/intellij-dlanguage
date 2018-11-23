package io.github.intellij.dlanguage.template

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

open class DlangContext protected constructor() : TemplateContextType("D", "D Language") {

    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        return file.name.endsWith(".d")
    }
}
