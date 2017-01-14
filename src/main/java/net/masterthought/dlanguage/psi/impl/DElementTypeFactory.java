package net.masterthought.dlanguage.psi.impl;

import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.stubs.types.DLanguageClassDeclarationStubElementType;
import net.masterthought.dlanguage.stubs.types.DLanguageFuncDeclarationStubElementType;
import net.masterthought.dlanguage.stubs.types.DLanguageIdentifierStubElementType;
import net.masterthought.dlanguage.stubs.types.DLanguageTemplateDeclarationStubElementType;
import org.jetbrains.annotations.NotNull;

public class DElementTypeFactory {
    private DElementTypeFactory(){}

    public static IElementType factory(@NotNull String name){
        if (name.equals("IDENTIFIER")) return new DLanguageIdentifierStubElementType(name);
        if (name.equals("FUNC_DECLARATION")) return new DLanguageFuncDeclarationStubElementType(name);
        if (name.equals("CLASS_DECLARATION")) return new DLanguageClassDeclarationStubElementType(name);
        if (name.equals("TEMPLATE_DECLARATION")) return new DLanguageTemplateDeclarationStubElementType(name);
        throw new RuntimeException("Unknown element type: " + name);
    }
}
