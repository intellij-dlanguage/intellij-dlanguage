package io.github.intellij.dlanguage

import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import javax.swing.Icon

/**
 * This class allows us to have different icons for D source files
 */
class DlangIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if(DLanguage.`is`(element.language)) {
            element.containingFile?.virtualFile?.let {
                return when(it.name) {
                    "app.d" -> DLanguage.Icons.SRC_FILE_RUNNABLE // common to have main entry point in app.d
                    "package.d" -> DLanguage.Icons.SRC_FILE_PACKAGE
                    else -> DLanguage.Icons.SRC_FILE
                }
            }

        }
        return null
    }
}
