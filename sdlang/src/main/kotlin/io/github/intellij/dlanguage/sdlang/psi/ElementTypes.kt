package io.github.intellij.dlanguage.sdlang.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import io.github.intellij.dlanguage.sdlang.SimpleDefinitionLanguage
import io.github.intellij.dlanguage.sdlang.psi.SDLangElementTypes.*

class SDLangTokenType(debugName: String) : IElementType(debugName, SimpleDefinitionLanguage)
class SDLangCompositeType(debugName: String) : IElementType(debugName, SimpleDefinitionLanguage)


val SDLANG_COMMENTS = TokenSet.create(LINE_COMMENT, BLOCK_COMMENT)
val SDLANG_STRINGS = TokenSet.create(RAW_STRING, REGULAR_STRING, CHARACTER)
