package io.github.intellij.dlanguage.features.formatter

import com.google.common.collect.Sets.newHashSet
import com.intellij.formatting.*
import com.intellij.formatting.alignment.AlignmentStrategy
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import io.github.intellij.dlanguage.features.formatter.impl.createSpacingBuilder
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.psi.DLanguageDeclarationsAndStatements
import io.github.intellij.dlanguage.psi.DLanguageStatement
import io.github.intellij.dlanguage.psi.DlangTypes.*
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration
import io.github.intellij.dlanguage.utils.DUtil.getPrevSiblingOfType
import io.github.intellij.dlanguage.utils.DeclarationOrStatement
import java.util.*


class DFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val element = formattingContext.psiElement
        val settings = formattingContext.codeStyleSettings

        val block = DFormattingBlock(element.node, AlignmentStrategy.getNullStrategy(), Indent.getNoneIndent(), null, createSpacingBuilder(settings))
        return FormattingModelProvider.createFormattingModelForPsiFile(element.containingFile, block, settings)
    }

    private class DFormattingBlock(node: ASTNode,
                                   private val myAlignmentStrategy: AlignmentStrategy,
                                   private val myIndent: Indent?,
                                   wrap: Wrap?,
                                   private val mySpacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, myAlignmentStrategy.getAlignment(node.elementType)) {

        override fun getIndent(): Indent? = myIndent

        override fun buildChildren(): List<Block> {
            val blocks = mutableListOf<Block>()
            var child: ASTNode? = myNode.firstChildNode
            while (child != null) {
                val childType = child.elementType
                if (child.textRange.length == 0) {
                    child = child.treeNext
                    continue
                }
                if (childType === TokenType.WHITE_SPACE) {
                    child = child.treeNext
                    continue
                }
                val substitutor = if (childType === BLOCK_COMMENT) LINE_COMMENT else childType
                val alignment = myAlignmentStrategy.getAlignment(substitutor)
                val e = buildSubBlock(child, alignment)
                blocks.add(e)
                child = child.treeNext
            }
            return Collections.unmodifiableList(blocks)
        }

        private fun buildSubBlock(child: ASTNode, alignment: Alignment?): DFormattingBlock {
            val indent = calcIndent(child)
            return DFormattingBlock(child, AlignmentStrategy.wrap(alignment), indent, null, mySpacingBuilder)
        }

        private fun calcIndent(child: ASTNode): Indent {
            val parentType = myNode.elementType
            val type = child.elementType
            if (type == DECLARATION_OR_STATEMENT) {
                if ((child.psi as DeclarationOrStatement).declaration?.oP_BRACES_LEFT != null) {
                    return Indent.getNoneIndent()
                }
                if ((child.psi as DeclarationOrStatement).statement?.statementNoCaseNoDefault?.blockStatement != null) {
                    return Indent.getNoneIndent()
                }
                if (parentType == IF_STATEMENT) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
                } else if (parentType == FOREACH_STATEMENT) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
                } else if (parentType == FOR_STATEMENT) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
                } else if (parentType == WHILE_STATEMENT) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
                } else if (parentType == CONDITIONAL_STATEMENT) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
                }
            } else if (type == DECLARATION) {
                if (parentType == CONDITIONAL_DECLARATION) {
                    return indentOfMultipleDeclarationChild(type, DECLARATION, LINE_COMMENT, BLOCK_COMMENT)
                }
            }
            if (type == OP_BRACES_RIGHT || type == OP_BRACES_LEFT) {
                return Indent.getNoneIndent()
            }
            if (getPrevSiblingOfType(child, OP_BRACES_LEFT, newHashSet(/*OP_BRACES_RIGHT,*/ KW_ELSE)) != null) {
                return Indent.getNormalIndent()
            }
            if (getPrevSiblingOfType(child, OP_BRACKET_LEFT, newHashSet(OP_BRACKET_RIGHT)) != null) {
                return Indent.getNormalIndent()
            }
            if (getPrevSiblingOfType(child, OP_PAR_LEFT, newHashSet(OP_PAR_RIGHT)) != null) {
                return Indent.getNormalIndent()
            }
//            if(type == TEMPLATE_PARAMETERS){
//                return Indent.getContinuationIndent()
//            }
//            if(parentType == EXPRESSION){
//                return Indent.getContinuationIndent()
//            }
            //todo continuation indent
            return Indent.getNoneIndent()
        }

        private fun indentOfMultipleDeclarationChild(childType: IElementType, vararg specType: IElementType): Indent {
            for (iElementType in specType) {
                if (iElementType == childType)
                    return Indent.getNormalIndent()
            }
            return Indent.getNoneIndent()
        }

        override fun getSpacing(child1: Block?, child2: Block): Spacing? {
            if (child1 is DFormattingBlock && child2 is DFormattingBlock) {
                val n1 = child1.node
                val n2 = child2.node
                val psi1 = n1.psi
                val psi2 = n2.psi

                if (psi1 is DLanguageDeclaration && psi2 is DLanguageDeclaration) {
                    return lineBreak()
                } else if (psi1.text == "{" && psi2 is DLanguageDeclarationsAndStatements) {
                    return lineBreak()
                } else if (psi1.text == "{" && psi2 is DLanguageDeclaration) {
                    return lineBreak()
                } else if (psi2.text == "}" && psi1 is DLanguageDeclarationsAndStatements) {
                    return lineBreak()
                } else if (psi2.text == "}" && psi1 is DLanguageDeclaration) {
                    return lineBreak()
                } else if (psi1 is DlangModuleDeclaration && psi2 is DLanguageDeclaration) {
                    return lineBreak()
                }

                //
                if (n1.elementType === OP_BRACES_LEFT && psi2 is DLanguageStatement) {
                    return lineBreak()
                }
//
//                 if (isTopLevelDeclaration(psi2) && (isTopLevelDeclaration(psi1) || n1.getElementType() == SEMICOLON)) {
//                     Different declarations should be separated by blank line
//                    boolean sameKind = psi1.getClass().equals(psi2.getClass())
//                        || psi1 instanceof DLanguageDeclDefs && psi2 instanceof DLanguageDeclDefs;
//                    return sameKind ? lineBreak() : lineBreak(1, true);
//                }
            }
            return mySpacingBuilder.getSpacing(this, child1, child2)
        }

        override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
            // This governs the indent on the new line when pressing the ENTER key
            val childIndent = Indent.getNoneIndent()

            val type = myNode.elementType
            if (type == BLOCK_STATEMENT || type == STRUCT_BODY || type == TEMPLATE_DECLARATION || type == CONDITIONAL_DECLARATION || type == CONDITIONAL_STATEMENT || type == DECLARATION) {
                return ChildAttributes(Indent.getNormalIndent(), null)
            }

            return ChildAttributes(childIndent, null)
        }

        override fun isLeaf(): Boolean = false // todo implement

        companion object {

//            private val TokenSet BLOCKS_TOKEN_SET = TokenSet.create(
//                BLOCK_STATEMENT,
//                STRUCT_DECLARATION,
//                INTERFACE_DECLARATION,
//                EXPRESSION_STATEMENT
//        )

            private val BRACES_TOKEN_SET = TokenSet.create(
                OP_BRACES_LEFT,
                OP_BRACES_RIGHT,
                OP_BRACKET_LEFT,
                OP_BRACKET_RIGHT,
                OP_PAR_LEFT,
                OP_PAR_RIGHT
            )

            private fun isTopLevelDeclaration(element: PsiElement): Boolean {
                return element is DlangModuleDeclaration
                    || element is io.github.intellij.dlanguage.psi.DLanguageImportDeclaration
                    || element is DLanguageDeclaration
                    || element is DLanguageStatement && element.getParent() is io.github.intellij.dlanguage.psi.DlangFile
            }

            private fun lineBreak(keepLineBreaks: Boolean = true): Spacing {
                return lineBreak(0, keepLineBreaks)
            }

            private fun lineBreak(lineBreaks: Int, keepLineBreaks: Boolean): Spacing {
                return Spacing.createSpacing(0, 0, lineBreaks + 1, keepLineBreaks, if (keepLineBreaks) 1 else 0)
            }

            private fun none(): Spacing {
                return Spacing.createSpacing(0, 0, 0, false, 0)
            }

            private fun one(): Spacing {
                return Spacing.createSpacing(1, 1, 0, false, 0)
            }
        }
    }

}
