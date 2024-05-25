package io.github.intellij.dlanguage.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.DLanguage
import javax.swing.Icon

/**
 * @author Samael Bate (singingbush)
 * created on 20/10/18
 */
abstract class DlangItemPresentation protected constructor(private val psiFile: PsiFile) : ItemPresentation {
    override fun getLocationString(): String? {
        return if (psiFile is DlangPsiFile) psiFile.getModuleName() else null
    }

    override fun getIcon(unused: Boolean): Icon {
        return DLanguage.Icons.FILE
    }
}
