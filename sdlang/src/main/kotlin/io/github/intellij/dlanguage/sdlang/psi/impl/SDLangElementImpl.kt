package io.github.intellij.dlanguage.sdlang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.sdlang.psi.SDLangElement

open class SDLangElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), SDLangElement
