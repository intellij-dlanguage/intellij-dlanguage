package io.github.intellij.dlanguage

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.OSFileIdeAssociation
import io.github.intellij.dlanguage.DlangBundle.message
import javax.swing.Icon

object DlangFileType : LanguageFileType(DLanguage), OSFileIdeAssociation {
    override fun getName(): String {
        return "D file"
    }

    override fun getDescription(): String {
        return message("dlang.filetype")
    }

    override fun getDefaultExtension(): String {
        return DEFAULT_EXTENSION
    }

    override fun getIcon(): Icon {
        return DLanguage.Icons.FILE
    }

    const val DEFAULT_EXTENSION: String = "d"
}
