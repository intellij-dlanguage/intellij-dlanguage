package io.github.intellij.dlanguage.sdlang

import com.intellij.lang.Language

object SimpleDefinitionLanguage : Language("SDLang") {
    override fun isCaseSensitive(): Boolean = true
}
