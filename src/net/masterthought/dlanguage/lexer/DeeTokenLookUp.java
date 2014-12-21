package net.masterthought.dlanguage.lexer;

import ddt.dtool.parser.DeeTokens;
import net.masterthought.dlanguage.psi.DElementType;

import java.util.HashMap;
import java.util.Map;

public class DeeTokenLookUp {

    private static Map<DeeTokens, DElementType> tokens;

    public static synchronized DElementType valueOf(DeeTokens type) {
        if (type == null) {
            return null;
        }
        if (tokens == null) {
            tokens = new HashMap<>();
        }

        // Check the cache map for a matching token
        DElementType tokenType = tokens.get(type);

        // If not in the map yet, create one and shove it in
        if (tokenType == null) {
            tokenType = new DElementType(type.name());
            tokens.put(type, tokenType);
        }

        return tokenType;
    }
}
