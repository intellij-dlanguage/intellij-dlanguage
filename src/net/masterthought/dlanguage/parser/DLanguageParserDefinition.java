package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.lexer.DLexer;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import net.masterthought.dlanguage.psi.ElementTypes;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes2;
import org.jetbrains.annotations.NotNull;

public class DLanguageParserDefinition implements ParserDefinition {

    public static final IStubFileElementType FILE_ELEMENT_TYPE = new IStubFileElementType(DLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(final Project project) {
        return new DLexer();
    }

    @Override
    public PsiParser createParser(final Project project) {
//        return new DParser2();
        return new DParser4();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE_ELEMENT_TYPE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return DLanguageTokenType.WHITESPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return DLanguageTokenType.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return DLanguageTokenType.STRING_LITERALS;
    }

    @NotNull
    @Override
    public PsiElement createElement(final ASTNode node) {
//        PsiElement ret = ElementTypes.Factory.createElement(node);
//        return ret;
        return DElementTypes2.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(final FileViewProvider viewProvider) {
        return new DLanguageFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(final ASTNode left, final ASTNode right) {
        // TODO: actually check input, return appropriate spacing requirements for same
        return SpaceRequirements.MAY;
    }
}
