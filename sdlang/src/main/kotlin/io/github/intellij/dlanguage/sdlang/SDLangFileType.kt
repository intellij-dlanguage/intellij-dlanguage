package io.github.intellij.dlanguage.sdlang

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.PlainTextFileType
import javax.swing.Icon

object SDLangFileType : LanguageFileType(SimpleDefinitionLanguage) {
    override fun getName(): String = "SDLang"
    override fun getDefaultExtension(): String = "sdl"
    override fun getDescription(): String = "Simple Definition Language"
    override fun getIcon(): Icon = PlainTextFileType.INSTANCE.icon
}
