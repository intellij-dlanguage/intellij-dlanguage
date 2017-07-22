package net.masterthought.dlanguage.features.formatter

import com.intellij.formatting.*
import com.intellij.formatting.alignment.AlignmentStrategy
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil.isAncestor
import com.intellij.util.containers.ContainerUtil
import net.masterthought.dlanguage.DLanguage
import net.masterthought.dlanguage.psi.*
import net.masterthought.dlanguage.psi.DLanguageTypes.*
import net.masterthought.dlanguage.utils.ForeachStatement
import net.masterthought.dlanguage.utils.IfStatement
import java.util.*


class DFormattingModelBuilder : FormattingModelBuilder {

    private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, DLanguage)
            .before(COMMA).spaceIf(false)
            .after(COMMA).spaceIf(true)
            .before(SEMICOLON).spaceIf(false)
            .after(SEMICOLON).spaceIf(true)
            .around(DOT).none()
            .before(ARGUMENT_LIST).none()
            .afterInside(OP_PAR_LEFT, ARGUMENT_LIST).none()
            .beforeInside(OP_PAR_RIGHT, ARGUMENT_LIST).none()
            .afterInside(OP_PAR_LEFT, PARAMETERS).none()
            .beforeInside(OP_PAR_RIGHT, PARAMETERS).none()
            .after(INTERFACE_DECLARATION).spaces(1)
            .after(KW_RETURN).spaces(1)
            .after(KW_CONTINUE).spaces(1)
            .after(KW_BREAK).spaces(1)
            .after(KW_FOREACH).spaces(1)
            .after(KW_FOR).spaces(1)
            .after(KW_IF).spaces(1)
            .after(KW_ELSE).spaces(1)
            .after(KW_CASE).spaces(1)
            .after(KW_SWITCH).spaces(1)
            .after(LINE_COMMENT).lineBreakInCode()
    }

    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        val block = DFormattingBlock(element.node, null, Indent.getNoneIndent(), null, settings, createSpacingBuilder(settings))
        return FormattingModelProvider.createFormattingModelForPsiFile(element.containingFile, block, settings)
    }

    override fun getRangeAffectingIndent(file: PsiFile, offset: Int, elementAtOffset: ASTNode): TextRange? {
        return null
    }

    private class DFormattingBlock(private val myNode: ASTNode,
                                   private val myAlignment: Alignment?,
                                   private val myIndent: Indent?,
                                   private val myWrap: Wrap?,
                                   private val mySettings: CodeStyleSettings,
                                   private val mySpacingBuilder: SpacingBuilder) : UserDataHolderBase(), ASTBlock {
        private var mySubBlocks: List<Block>? = null

        override fun getNode(): ASTNode {
            return myNode
        }

        override fun getTextRange(): TextRange {
            return myNode.textRange
        }

        override fun getWrap(): Wrap? {
            return myWrap
        }

        override fun getIndent(): Indent? {
            return myIndent
        }

        override fun getAlignment(): Alignment? {
            return myAlignment
        }

        override fun getSubBlocks(): List<Block> {
            if (mySubBlocks == null) {
                mySubBlocks = buildSubBlocks()
            }
            return ContainerUtil.newArrayList(mySubBlocks!!)
        }

        private fun buildSubBlocks(): List<Block> {
            val strategy: AlignmentStrategy.AlignmentPerTypeStrategy? = null
            val blocks = ContainerUtil.newArrayList<Block>()
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
                val alignment = strategy?.getAlignment(substitutor)
                val e = buildSubBlock(child, alignment)
                blocks.add(e)
                child = child.treeNext
            }
            return Collections.unmodifiableList(blocks)
        }

        private fun buildSubBlock(child: ASTNode, alignment: Alignment?): DFormattingBlock {
            val indent = calcIndent(child)
            return DFormattingBlock(child, alignment, indent, null, mySettings, mySpacingBuilder)
        }


        private fun calcIndent(child: ASTNode): Indent {
            val parentType = myNode.elementType
            val type = child.elementType
            if (parentType === BLOCK_STATEMENT)
                return indentOfMultipleDeclarationChild(type, DECLARATIONS_AND_STATEMENTS, LINE_COMMENT, BLOCK_COMMENT)
            else if (parentType === STRUCT_BODY)
                return indentOfMultipleDeclarationChild(type, DECLARATION, LINE_COMMENT, BLOCK_COMMENT)
            else if (parentType === CONDITIONAL_DECLARATION)
                return indentOfMultipleDeclarationChild(type, DECLARATION, LINE_COMMENT, BLOCK_COMMENT)
            else if (parentType === CONDITIONAL_STATEMENT) {
                if ((myNode.psi as DLanguageConditionalStatement).declarationOrStatements[0].declaration?.oP_BRACES_LEFT != null) {
                    return Indent.getNoneIndent()
                }
                if ((myNode.psi as DLanguageConditionalStatement).declarationOrStatements[0].statement?.statementNoCaseNoDefault?.blockStatement != null) {
                    return Indent.getNoneIndent()
                }
                return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
            } else if (parentType == IF_STATEMENT || parentType == FOR_STATEMENT || parentType == FOREACH_STATEMENT || parentType == WHILE_STATEMENT || parentType == DO_STATEMENT || parentType == CASE_RANGE_STATEMENT || parentType == CASE_STATEMENT || parentType == SWITCH_STATEMENT) {
                if (parentType == IF_STATEMENT) {
                    if ((myNode.psi as DLanguageIfStatement).declarationOrStatements[0].declaration?.oP_BRACES_LEFT != null) {
                        return Indent.getNoneIndent()
                    }
                    if ((myNode.psi as DLanguageIfStatement).declarationOrStatements[0].statement?.statementNoCaseNoDefault?.blockStatement != null) {
                        return Indent.getNoneIndent()
                    }
                    if ((myNode.psi as IfStatement).kW_ELSE != null) {
                        if ((myNode.psi as DLanguageIfStatement).declarationOrStatements[1].declaration?.oP_BRACES_LEFT != null && isAncestor((myNode.psi as DLanguageIfStatement).declarationOrStatements[1], child.psi, true)) {
                            return Indent.getNoneIndent()
                        }
                        if ((myNode.psi as DLanguageIfStatement).declarationOrStatements[1].statement?.statementNoCaseNoDefault?.blockStatement != null && isAncestor((myNode.psi as DLanguageIfStatement).declarationOrStatements[1], child.psi, true)) {
                            return Indent.getNoneIndent()
                        }
                    }
                }
                if (parentType == FOREACH_STATEMENT) {
                    if ((myNode.psi as ForeachStatement).declarationOrStatement?.declaration?.oP_BRACES_LEFT != null) {
                        return Indent.getNoneIndent()
                    }
                    if ((myNode.psi as ForeachStatement).declarationOrStatement?.statement?.statementNoCaseNoDefault?.blockStatement != null) {
                        return Indent.getNoneIndent()
                    }
                }
                return indentOfMultipleDeclarationChild(type, DECLARATION_OR_STATEMENT, LINE_COMMENT, BLOCK_COMMENT)
            } else if (parentType === ENUM_BODY)
                return indentOfMultipleDeclarationChild(type, ENUM_MEMBER, LINE_COMMENT, BLOCK_COMMENT)//todo are the commas indented?
            else if (parentType === DECLARATION)
                return indentOfMultipleDeclarationChild(type, DECLARATION, LINE_COMMENT, BLOCK_COMMENT)//todo are the commas indented?
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
                } else if (psi1 is DLanguageModuleDeclaration && psi2 is DLanguageDeclaration) {
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

            val parentType = myNode.elementType
            //
            //            if (BLOCKS_TOKEN_SET.contains(parentType) ||
            //                parentType == IMPORT_DECLARATION ||
            //                parentType == BLOCK_STATEMENT ||
            //                parentType == STRUCT_DECLARATION ||
            //                parentType == ARGUMENT_LIST) {
            //                childIndent = Indent.getNormalIndent();
            //            }

            return ChildAttributes(childIndent, null)
        }

        override fun isIncomplete(): Boolean {
            return false
        }

        override fun isLeaf(): Boolean {
            return false
        }

        companion object {

            //        private static final TokenSet BLOCKS_TOKEN_SET = TokenSet.create(
            //            BLOCK_STATEMENT,
            //            STRUCT_DECLARATION,
            //            INTERFACE_DECLARATION,
            //            EXPRESSION_STATEMENT
            //        );

            private val BRACES_TOKEN_SET = TokenSet.create(
                OP_BRACES_LEFT,
                OP_BRACES_RIGHT,
                OP_BRACKET_LEFT,
                OP_BRACKET_RIGHT,
                OP_PAR_LEFT,
                OP_PAR_RIGHT
            )

            private fun isTopLevelDeclaration(element: PsiElement): Boolean {
                return element is DLanguageModuleDeclaration
                    || element is DLanguageImportDeclaration
                    || element is DLanguageDeclaration
                    || element is DLanguageStatement && element.getParent() is DLanguageFile
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
