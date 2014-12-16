package net.masterthought.dlanguage.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Parser for D source, wrapper for Descent parser.
 */
public class DParser implements PsiParser {

    public DParser() { }


    @NotNull
    @Override
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
/*
        // Create a Descent parser, and parse into AST tree
        // Doesn't actually use previous lexation
        Parser parser = new Parser(Parser.D2, builder.getOriginalText().toString());
        Module module = parser.parseModuleObj();
*/

        // Now convert Descent AST tree into IntelliJ types
        // TODO
        return null;
    }
}
