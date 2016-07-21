package net.masterthought.dlanguage;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.lexer.DLanguageLexer;
import net.masterthought.dlanguage.parser.DLanguageParser;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageTypes;
import net.masterthought.dlanguage.stubs.types.DFileStubElementType;
import org.jetbrains.annotations.NotNull;

public class DLanguageParserDefinition implements ParserDefinition {

    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(DLanguageTypes.LINE_COMMENT, DLanguageTypes.BLOCK_COMMENT, DLanguageTypes.NESTING_BLOCK_COMMENT);

    public static final IStubFileElementType FILE_ELEMENT_TYPE = DFileStubElementType.INSTANCE;

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new FlexAdapter(new DLanguageLexer(null));
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new DLanguageParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE_ELEMENT_TYPE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new DLanguageFile(viewProvider);
    }

    public ParserDefinition.SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return ParserDefinition.SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return DLanguageTypes.Factory.createElement(node);
    }

}
