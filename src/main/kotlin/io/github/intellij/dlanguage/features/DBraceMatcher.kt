package io.github.intellij.dlanguage.features

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import io.github.intellij.dlanguage.psi.DTokenSets
import io.github.intellij.dlanguage.psi.DlangTypes

class DBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> {
        return PAIRS
    }

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ): Boolean {
        return (alwaysMatch.contains(lbraceType)
                || DTokenSets.WHITESPACES.contains(contextType)
                || DTokenSets.LINE_COMMENTS.contains(contextType)
                || DTokenSets.BLOCK_COMMENTS.contains(contextType)) || DlangTypes.OP_PAR_RIGHT === contextType || null == contextType
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    companion object {
        private val PAIRS = arrayOf(
            BracePair(DlangTypes.PARENTHESES_LEFT, DlangTypes.PARENTHESES_RIGHT, false),
            BracePair(DlangTypes.BRACES_LEFT, DlangTypes.BRACES_RIGHT, true),
            BracePair(DlangTypes.BRACKETS_LEFT, DlangTypes.BRACKETS_RIGHT, false)
        )
        private val alwaysMatch = TokenSet.create(DlangTypes.OP_BRACES_LEFT)
    }
}
