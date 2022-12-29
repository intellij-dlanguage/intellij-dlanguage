package io.github.intellij.dlanguage.sdlang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import io.github.intellij.dlanguage.sdlang.SDLangFileType
import io.github.intellij.dlanguage.sdlang.SimpleDefinitionLanguage

class SDLangFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, SimpleDefinitionLanguage) {
    override fun getFileType(): FileType = SDLangFileType
}
