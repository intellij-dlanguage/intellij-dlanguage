package io.github.intellij.dlanguage.sdlang.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import io.github.intellij.dlanguage.sdlang.psi.SDLangElementTypes.*

class SDLangQuoteHandler : SimpleTokenSetQuoteHandler(REGULAR_STRING, RAW_STRING, CHARACTER)
