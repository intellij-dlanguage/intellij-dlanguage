package io.github.intellij.dlanguage.jps.model;

import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.ex.JpsElementTypeWithDummyProperties;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

public class DLanguageIncludeSourceRootType extends JpsElementTypeWithDummyProperties implements JpsModuleSourceRootType<JpsDummyElement> {
    public static final DLanguageIncludeSourceRootType INSTANCE = new DLanguageIncludeSourceRootType();

    private DLanguageIncludeSourceRootType() {
    }
}
