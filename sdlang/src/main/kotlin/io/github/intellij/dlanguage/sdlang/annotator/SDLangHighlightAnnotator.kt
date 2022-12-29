package io.github.intellij.dlanguage.sdlang.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.sdlang.colors.SDLangColor
import io.github.intellij.dlanguage.sdlang.psi.SDLangTagId

class SDLangHighlightAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is SDLangTagId) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element)
                .textAttributes(SDLangColor.TAG_IDENTIFIER.textAttributesKey).create()
        }
    }
}
