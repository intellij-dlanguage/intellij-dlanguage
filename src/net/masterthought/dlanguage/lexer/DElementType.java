package net.masterthought.dlanguage.lexer;

import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeTokens;
import net.masterthought.dlanguage.DLanguage;


/**
 * An element of the D language, for tokenizing.
 * Each instance represents a type of token (as distinct from an actual
 * tokenized bit of source).  Wraps the ParseD TokenType.
 *
 * TODO: subclass for keyword tokens, ala com.intellij.psi.tree.IKeywordElementType?
 */
public class DElementType extends IElementType {

    private final DeeTokens type;

    public DElementType(final DeeTokens type) {
        super(type.name(), DLanguage.INSTANCE);

        this.type = type;
    }

    /**
     * @return ParserD TokenType of this element
     */
    public DeeTokens getType() {
        return type;
    }

}
