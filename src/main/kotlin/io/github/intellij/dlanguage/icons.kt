package io.github.intellij.dlanguage

import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtil
import io.github.intellij.dlanguage.icons.DlangIcons
import javax.swing.Icon

/**
 * This class allows us to have different icons for D source files
 */
class DlangIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if(DLanguage.`is`(element.language)) {
            PsiUtil.getVirtualFile(element)?.let {
                return when(it.name) {
                    "app.d" -> DlangIcons.SRC_FILE_RUNNABLE // common to have main entry point in app.d
                    "package.d" -> DlangIcons.SRC_FILE_PACKAGE
                    else -> DlangIcons.SRC_FILE
                }
            }

        }
        return null
    }
}
