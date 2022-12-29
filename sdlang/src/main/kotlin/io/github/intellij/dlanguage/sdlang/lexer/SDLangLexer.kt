package io.github.intellij.dlanguage.sdlang.lexer

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class SDLangLexer : FlexAdapter(_SDLangLexer(null as Reader?))
