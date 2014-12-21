package net.masterthought.dlanguage.lexer;

import ddt.dtool.parser.DeeTokens;
import net.masterthought.dlanguage.psi.DElementType;

import java.util.HashMap;
import java.util.Map;

public class DeeElementTypeCache {

    private static Map<String, DElementType> elements;

    public static synchronized DElementType valueOf(String type) {
        if (type == null) {
            return null;
        }
        if (elements == null) {
            elements = new HashMap<>();
        }

        // Check the cache map for a matching token
        DElementType tokenType = elements.get(type);

        // If not in the map yet, create one and shove it in
        if (tokenType == null) {
            tokenType = new DElementType(type);
            elements.put(type, tokenType);
        }

        return tokenType;
    }
}
