package net.masterthought.dlanguage.features.formatter;

import com.intellij.formatting.*;
import com.intellij.formatting.alignment.AlignmentStrategy;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    private static SpacingBuilder createSpacingBuilder(@NotNull CodeStyleSettings settings) {
        return new SpacingBuilder(settings, DLanguage.INSTANCE)
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
            .after(IMPORT).spaces(1)
            .after(INTERFACE).spaces(1)
            .after(KW_RETURN).spaces(1)
            .after(KW_CONTINUE).spaces(1)
            .after(KW_BREAK).spaces(1)
            .after(KW_FOREACH).spaces(1)
            .after(KW_FOR).spaces(1)
            .after(KW_IF).spaces(1)
            .after(KW_ELSE).spaces(1)
            .before(ELSE_STATEMENT).spaces(1)
            .after(KW_CASE).spaces(1)
            .after(KW_SWITCH).spaces(1)
            .after(LINE_COMMENT).lineBreakInCode()
            .after(BLOCK_COMMENT).lineBreakInCode()
            ;
    }

    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        Block block = new DFormattingBlock(element.getNode(), null, Indent.getNoneIndent(), null, settings, createSpacingBuilder(settings));
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), block, settings);
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }

    private static class DFormattingBlock extends UserDataHolderBase implements ASTBlock {
        private static final TokenSet BRACES_TOKEN_SET = TokenSet.create(
            OP_BRACES_LEFT,
            OP_BRACES_RIGHT,
            OP_BRACKET_LEFT,
            OP_BRACKET_RIGHT,
            OP_PAR_LEFT,
            OP_PAR_RIGHT
        );

        @NotNull
        private final ASTNode myNode;
        @Nullable
        private final Alignment myAlignment;
        @Nullable
        private final Indent myIndent;
        @Nullable
        private final Wrap myWrap;
        @NotNull
        private final CodeStyleSettings mySettings;
        @NotNull
        private final SpacingBuilder mySpacingBuilder;
        @Nullable
        private List<Block> mySubBlocks;

        private DFormattingBlock(@NotNull ASTNode node,
                                 @Nullable Alignment alignment,
                                 @Nullable Indent indent,
                                 @Nullable Wrap wrap,
                                 @NotNull CodeStyleSettings settings,
                                 @NotNull SpacingBuilder spacingBuilder) {
            myNode = node;
            myAlignment = alignment;
            myIndent = indent;
            myWrap = wrap;
            mySettings = settings;
            mySpacingBuilder = spacingBuilder;
        }

        private static boolean isTopLevelDeclaration(@NotNull PsiElement element) {
            return element instanceof DLanguageModuleDeclaration
                || element instanceof DLanguageImportList
                || element instanceof DLanguageDeclDefs
                || element instanceof DLanguageStatement
                && element.getParent() instanceof DLanguageFile;
        }

        private static Spacing lineBreak() {
            return lineBreak(true);
        }

        private static Spacing lineBreak(boolean keepLineBreaks) {
            return lineBreak(0, keepLineBreaks);
        }

        private static Spacing lineBreak(int lineBreaks, boolean keepLineBreaks) {
            return Spacing.createSpacing(0, 0, lineBreaks + 1, keepLineBreaks, keepLineBreaks ? 1 : 0);
        }

        private static Spacing none() {
            return Spacing.createSpacing(0, 0, 0, false, 0);
        }

        private static Spacing one() {
            return Spacing.createSpacing(1, 1, 0, false, 0);
        }

        @Override
        public ASTNode getNode() {
            return myNode;
        }

        @NotNull
        @Override
        public TextRange getTextRange() {
            return myNode.getTextRange();
        }

        @Nullable
        @Override
        public Wrap getWrap() {
            return myWrap;
        }

        @Nullable
        @Override
        public Indent getIndent() {
            return myIndent;
        }

        @Nullable
        @Override
        public Alignment getAlignment() {
            return myAlignment;
        }

        @NotNull
        @Override
        public List<Block> getSubBlocks() {
            if (mySubBlocks == null) {
                mySubBlocks = buildSubBlocks();
            }
            return ContainerUtil.newArrayList(mySubBlocks);
        }

        @NotNull
        private List<Block> buildSubBlocks() {
            AlignmentStrategy.AlignmentPerTypeStrategy strategy = null;
            List<Block> blocks = ContainerUtil.newArrayList();
            for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
                IElementType childType = child.getElementType();
                if (child.getTextRange().getLength() == 0) continue;
                if (childType == TokenType.WHITE_SPACE) continue;
                IElementType substitutor = childType == BLOCK_COMMENT ? LINE_COMMENT : childType;
                Alignment alignment = strategy != null ? strategy.getAlignment(substitutor) : null;
                DFormattingBlock e = buildSubBlock(child, alignment);
                blocks.add(e);
            }
            return Collections.unmodifiableList(blocks);
        }

        @NotNull
        private DFormattingBlock buildSubBlock(@NotNull ASTNode child, @Nullable Alignment alignment) {
            Indent indent = calcIndent(child);
            return new DFormattingBlock(child, alignment, indent, null, mySettings, mySpacingBuilder);
        }


        @NotNull
        private Indent calcIndent(@NotNull ASTNode child) {
            IElementType parentType = myNode.getElementType();
            IElementType type = child.getElementType();
            if (parentType == IMPORT_DECLARATION) return indentOfMultipleDeclarationChild(type, IMPORT_LIST);
            return Indent.getNoneIndent();
        }

        private Indent indentOfMultipleDeclarationChild(@NotNull IElementType childType, @NotNull IElementType specType) {
            if (childType == specType) {
                return Indent.getNormalIndent();
            }
            return Indent.getNoneIndent();
        }

        @Override
        public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
            if (child1 instanceof DFormattingBlock && child2 instanceof DFormattingBlock) {
                ASTNode n1 = ((DFormattingBlock) child1).getNode();
                ASTNode n2 = ((DFormattingBlock) child2).getNode();
                PsiElement psi1 = n1.getPsi();
                PsiElement psi2 = n2.getPsi();
                if (n1.getElementType() == DECL_DEF && psi2 instanceof DLanguageType) return one();


                if (psi1 instanceof DLanguageStatement && psi2 instanceof DLanguageStatement) {
                    return lineBreak();
                }
                if (isTopLevelDeclaration(psi2) && (isTopLevelDeclaration(psi1) || n1.getElementType() == SEMICOLON)) {
                    boolean sameKind = psi1.getClass().equals(psi2.getClass())
                        || psi1 instanceof DLanguageDeclDef && psi2 instanceof DLanguageDeclDef;
                    return sameKind ? lineBreak() : lineBreak(1, true);
                }
            }
            return mySpacingBuilder.getSpacing(this, child1, child2);
        }

        @NotNull
        @Override
        public ChildAttributes getChildAttributes(int newChildIndex) {
            Indent childIndent = Indent.getNoneIndent();
            return new ChildAttributes(childIndent, null);
        }

        @Override
        public boolean isIncomplete() {
            return false;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    }

}
