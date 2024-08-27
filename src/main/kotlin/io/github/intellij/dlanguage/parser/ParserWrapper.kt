package io.github.intellij.dlanguage.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

/**
 * Created by francis on 7/2/2017.
 */
class ParserWrapper : PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val parser = DLangParser(builder)
        val m = builder.mark()
        parser.parseModule()
        m.done(root)
        return builder.treeBuilt
    }
}
