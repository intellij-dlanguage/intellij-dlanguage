package net.masterthought.dlanguage.lexer;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DElementType;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import net.masterthought.dlanguage.stubs.types.DDefinitionFunctionStubElementType;

import java.util.HashMap;
import java.util.Map;

public class DeeElementTypeCache {

    private static Map<String, IElementType> elements;

    public static synchronized IElementType valueOf(String type) {
        if (type == null) {
            return null;
        }
        if (elements == null) {
            elements = new HashMap<>();
        }

        // Check the cache map for a matching token
        IElementType tokenType = elements.get(type);

        // If not in the map yet, create one and shove it in
        if (tokenType == null) {


            if(tokenType == DElementTypes.DEFINITION_FUNCTION){
                tokenType = new DDefinitionFunctionStubElementType(type);
            } else {
                tokenType = new DElementType(type);
            }

            elements.put(type, tokenType);
        }

        return tokenType;
    }
}
