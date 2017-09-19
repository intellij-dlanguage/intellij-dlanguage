package io.github.intellij.dlanguage.jps.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsCompositeElementBase;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;
import org.jetbrains.jps.model.module.JpsModule;

import java.util.Collections;
import java.util.List;


public class JpsDLanguageModuleExtension extends JpsCompositeElementBase<JpsDLanguageModuleExtension> {
    public static final JpsElementChildRole<JpsDLanguageModuleExtension> ROLE = JpsElementChildRoleBase.create("DLanguage");

    private final DLanguageModuleExtensionProperties myProperties;

    @SuppressWarnings("UnusedDeclaration")
    public JpsDLanguageModuleExtension() {
        myProperties = new DLanguageModuleExtensionProperties();
    }

    public JpsDLanguageModuleExtension(final DLanguageModuleExtensionProperties properties) {
        myProperties = properties;
    }

    public JpsDLanguageModuleExtension(final JpsDLanguageModuleExtension moduleExtension) {
        myProperties = new DLanguageModuleExtensionProperties(moduleExtension.myProperties);
    }

    @Nullable
    public static JpsDLanguageModuleExtension getExtension(@Nullable final JpsModule module) {
        return module != null ? module.getContainer().getChild(ROLE) : null;
    }

    @NotNull
    @Override
    public JpsDLanguageModuleExtension createCopy() {
        return new JpsDLanguageModuleExtension(this);
    }

    public DLanguageModuleExtensionProperties getProperties() {
        return myProperties;
    }

    public List<String> getParseTransforms() {
        return Collections.unmodifiableList(myProperties.myParseTransforms);
    }
}
