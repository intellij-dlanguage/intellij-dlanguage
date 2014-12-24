package net.masterthought.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.stubs.types.DDefinitionFunctionStubElementType;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory() {}

    public static IElementType factory(@NotNull String name) {
        if (name.equals("DEFINITION_FUNCTION")) return new DDefinitionFunctionStubElementType(name);
        throw new RuntimeException("Unknown element type: " + name);
    }
}

