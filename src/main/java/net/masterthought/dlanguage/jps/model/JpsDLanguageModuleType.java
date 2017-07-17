package net.masterthought.dlanguage.jps.model;

import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.ex.JpsElementTypeWithDummyProperties;
import org.jetbrains.jps.model.module.JpsModuleType;

/**
 * Empty shell DLanguageModuleType-alike.
 */
public class JpsDLanguageModuleType extends JpsElementTypeWithDummyProperties implements JpsModuleType<JpsDummyElement> {
    public static final JpsDLanguageModuleType INSTANCE = new JpsDLanguageModuleType();

    private JpsDLanguageModuleType() {
    }
}
