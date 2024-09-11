package io.github.intellij.dlanguage.folding

import com.intellij.application.options.CodeStyle
import com.intellij.codeInsight.folding.CodeFoldingSettings
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.DlangTypes.*
import io.github.intellij.dlanguage.psi.ext.*
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.named.DLanguageTemplateDeclaration
import io.github.intellij.dlanguage.utils.ImportDeclaration
import java.util.ArrayList

class DFoldingBuilder : FoldingBuilderEx(), DumbAware {
    private companion object {
        const val ONE_LINER_PLACEHOLDERS_EXTRA_LENGTH = 4
    }

    private fun getPlaceholderTextForPsi(psi: PsiElement): String =
        when (psi) {
            is PsiComment -> {
                "/* ... */"
            }
            is DLanguageArrayInitializer -> "[...]"
            is DLanguageStructBody,
            is DLanguageBlockStatement,
            is DLanguageTemplateDeclaration,
            is DLanguageStructInitializer,
            is DLanguageAnonymousEnumDeclaration,
            is DLanguageEnumBody,
            is DLanguageDeclarationBlock,
            is DLanguageConditionalDeclaration -> "{...}"
            is DLanguageLinkageAttribute -> {
                when {
                    psi.isExternal -> "{...}"
                    else -> "..."
                }
            }
            else -> "..."
        }

    override fun getPlaceholderText(node: ASTNode): String =
        when (node.elementType) {
            //OP_BRACES_LEFT -> " { "
            //OP_BRACES_RIGHT -> " }"
            else -> getPlaceholderTextForPsi(node.psi)
        }

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        if (root !is DlangPsiFile)
            return emptyArray()

        val descriptors: MutableList<FoldingDescriptor> = ArrayList()
        val rightMargin = CodeStyle.getSettings(root.containingFile).getRightMargin(DLanguage)
        val visitor = FoldingVisitor(descriptors, rightMargin)
        PsiTreeUtil.processElements(root) {
            // required in case the psi element has been deleted
            if (it.isPhysical && it.isValid && it.isWritable && it.text != "") {
                it.accept(visitor)
                true
            } else {
                false
            }
        }

        return descriptors.toTypedArray()
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean =
        when {
            node.psi is DLanguageImportDeclaration ->
                CodeFoldingSettings.getInstance().COLLAPSE_IMPORTS

            node.elementType in TokenSet.create(OP_BRACES_LEFT, OP_BRACES_RIGHT) ->
                DCodeFoldingSettings.instance.collapsibleOneLineMethods

            else -> false
        }

    private class FoldingVisitor(
        private val descriptors: MutableList<FoldingDescriptor>,
        val rightMargin: Int
    ) : DlangVisitor() {
        private fun fold(element: PsiElement) {
            descriptors += FoldingDescriptor(element.node, element.textRange)
        }

        override fun visitBlockStatement(o: DLanguageBlockStatement) {
            if (tryFoldBlockWhitespaces(o)) return
            fold(o)
        }

        override fun visitDeclarationBlock(o: DLanguageDeclarationBlock) {
            fold(o)
        }

        override fun visitStructBody(o: DLanguageStructBody) = fold(o)

        override fun visitTemplateDeclaration(o: DLanguageTemplateDeclaration) =
            foldBetween(o, o.leftBraces, o.rightBraces)

        override fun visitArrayInitializer(o: DLanguageArrayInitializer) = fold(o)

        override fun visitStructInitializer(o: DLanguageStructInitializer) = fold(o)

        private fun foldBetween(element: PsiElement, left: PsiElement?, right: PsiElement?) {
            if (left != null && right != null) {
                val range = TextRange(left.textOffset, right.textOffset + 1)
                descriptors += FoldingDescriptor(element.node, range)
            }
        }

        override fun visitLinkageAttribute(o: DLanguageLinkageAttribute) {
            if (o.isExternal) {
                val decl = o.parent as? Declaration ?: return
                foldBetween(o, decl.leftBraces, decl.rightBraces)
            }
        }

        override fun visitAnonymousEnumDeclaration(o: DLanguageAnonymousEnumDeclaration) =
            foldBetween(o, o.leftBraces, o.rightBraces)

        override fun visitEnumBody(o: DLanguageEnumBody) =
            fold(o)

        // version statement
        override fun visitConditionalDeclaration(o: DLanguageConditionalDeclaration) =
            foldBetween(o, o.leftBraces, o.rightBraces)

        override fun visitComment(comment: PsiComment) {
            if (comment.prevSibling is PsiComment) return
            var lastTrailing = comment

            while (true) {
                if (lastTrailing.nextSibling == null) break
                if (lastTrailing.nextSibling !is PsiComment) break

                lastTrailing = lastTrailing.nextSibling as PsiComment
            }

            val range = TextRange(comment.textOffset, lastTrailing.textRange.endOffset)
            descriptors += FoldingDescriptor(comment.node, range)
        }

        override fun visitImportDeclaration(importDecl: DLanguageImportDeclaration) {
            // Skip if previous line is import declaration
            if (importDecl.getPrevNonCommentSibling() is ImportDeclaration) return

            // Fold trailing imports
            var lastTrailing: ImportDeclaration = importDecl

            while (true) {
                val nextSibling = lastTrailing.getNextNonCommentSibling()
                if (nextSibling !is ImportDeclaration) break

                lastTrailing = nextSibling
            }

            // Don’t fold 1 line imports
            if (lastTrailing == importDecl)
                return

            val startOffset = importDecl.kW_IMPORT?.textRange?.endOffset ?: return
            val endOffset = lastTrailing.textRange.endOffset
            if (startOffset + 1 >= endOffset) return
            val range = TextRange(startOffset + 1, endOffset)
            assert(!range.isEmpty)
            descriptors += FoldingDescriptor(importDecl, range)
        }

        private fun tryFoldBlockWhitespaces(block: DLanguageBlockStatement): Boolean {
            val doc = PsiDocumentManager.getInstance(block.project).getDocument(block.containingFile) ?: return false
            val maxLength = rightMargin - block.getOffsetInLine(doc) - ONE_LINER_PLACEHOLDERS_EXTRA_LENGTH
            if (!block.isSingleLine(doc, maxLength)) return false

            val leftBrace = block.oP_BRACES_LEFT ?: return false
            val rightBrace = block.oP_BRACES_RIGHT ?: return false

            // void foo() { \n
            //     writeln("Hello world"); \n
            // }
            if (block.statements.size < 2) return false

            /// Get previous whitespace if exist as start region
            val leftEl = leftBrace.parent?.prevSibling as? PsiWhiteSpace ?: leftBrace
            val body = block.statements
            if (body.isEmpty()) return false

            val range1 = TextRange(leftEl.textOffset, body.first().textOffset)
            val range2 = TextRange(body.last().textRange.endOffset, rightBrace.textRange.endOffset)

            val group = FoldingGroup.newGroup("one-liner")
            descriptors += FoldingDescriptor(leftBrace.node, range1, group)
            descriptors += FoldingDescriptor(rightBrace.node, range2, group)

            return true
        }
    }
}

private fun PsiElement.getOffsetInLine(doc: Document): Int {
    val blockLine = doc.getLineNumber(textRange.startOffset)
    return leftLeaves
        .takeWhile { doc.getLineNumber(it.textRange.endOffset) == blockLine }
        .sumOf { el -> el.text.lastIndexOf('\n').let { el.text.length - Integer.max(it + 1, 0) } }
}

private fun DLanguageBlockStatement.isSingleLine(doc: Document, maxLength: Int): Boolean {
    // remove all leading and trailing spaces before counting lines
    val startContents = oP_BRACES_LEFT?.rightSiblings?.dropWhile { it is PsiWhiteSpace }?.firstOrNull() ?: return false
    if (startContents.node.elementType == DlangTypes.OP_BRACES_RIGHT) return false
    val endContents = oP_BRACES_RIGHT?.leftSiblings?.dropWhile { it is PsiWhiteSpace }?.firstOrNull() ?: return false
    if (endContents.textRange.endOffset - startContents.textOffset > maxLength) return false

    return doc.getLineNumber(startContents.textOffset) == doc.getLineNumber(endContents.textRange.endOffset)
}

private val PsiElement.leftBraces: PsiElement?
    get() = node.findChildByType(DlangTypes.OP_BRACES_LEFT)?.psi

private val PsiElement.rightBraces: PsiElement?
    get() = node.findChildByType(DlangTypes.OP_BRACES_RIGHT)?.psi

private val DLanguageLinkageAttribute.isExternal: Boolean
    get() = node.findChildByType(DlangTypes.KW_EXTERN) != null
