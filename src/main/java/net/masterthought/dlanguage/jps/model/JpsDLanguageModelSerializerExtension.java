package net.masterthought.dlanguage.jps.model;


import com.intellij.util.xmlb.SkipDefaultValuesSerializationFilters;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.JpsDummyElement;
import org.jetbrains.jps.model.JpsElement;
import org.jetbrains.jps.model.JpsElementFactory;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.serialization.JpsModelSerializerExtension;
import org.jetbrains.jps.model.serialization.JpsProjectExtensionSerializer;
import org.jetbrains.jps.model.serialization.facet.JpsFacetConfigurationSerializer;
import org.jetbrains.jps.model.serialization.library.JpsSdkPropertiesSerializer;
import org.jetbrains.jps.model.serialization.module.JpsModulePropertiesSerializer;
import org.jetbrains.jps.model.serialization.module.JpsModuleSourceRootDummyPropertiesSerializer;
import org.jetbrains.jps.model.serialization.module.JpsModuleSourceRootPropertiesSerializer;

import java.util.Collections;
import java.util.List;

/**
 * Main entry point for the serializer server.
 */
public class JpsDLanguageModelSerializerExtension extends JpsModelSerializerExtension {
    public static final String DLANGUAGE_SDK_TYPE_ID = "D Language SDK";

    @NotNull
    @Override
    public List<? extends JpsModuleSourceRootPropertiesSerializer<?>> getModuleSourceRootPropertiesSerializers() {
        return Collections.singletonList(new JpsModuleSourceRootDummyPropertiesSerializer(DLanguageIncludeSourceRootType.INSTANCE, "dlanguage-include"));
    }

    @NotNull
    @Override
    public List<? extends JpsModulePropertiesSerializer<?>> getModulePropertiesSerializers() {
        return Collections.singletonList(new JpsModulePropertiesSerializer<JpsDummyElement>(JpsDLanguageModuleType.INSTANCE, "DLANGUAGE_MODULE", null) {
            @Override
            public JpsDummyElement loadProperties(@Nullable final Element componentElement) {
                return JpsElementFactory.getInstance().createDummyElement();
            }

            @Override
            public void saveProperties(@NotNull final JpsDummyElement properties, @NotNull final Element componentElement) {
            }
        });
    }

    @NotNull
    @Override
    public List<? extends JpsSdkPropertiesSerializer<?>> getSdkPropertiesSerializers() {
        return Collections.singletonList(new JpsSdkPropertiesSerializer<JpsDummyElement>(DLANGUAGE_SDK_TYPE_ID, JpsDLanguageSdkType.INSTANCE) {
            @NotNull
            @Override
            public JpsDummyElement loadProperties(@Nullable final Element propertiesElement) {
                return JpsElementFactory.getInstance().createDummyElement();
            }

            @Override
            public void saveProperties(@NotNull final JpsDummyElement properties, @NotNull final Element element) {
            }
        });
    }

    @NotNull
    @Override
    public List<? extends JpsFacetConfigurationSerializer<?>> getFacetConfigurationSerializers() {
        return Collections.singletonList(new JpsFacetConfigurationSerializer<JpsDLanguageModuleExtension>(JpsDLanguageModuleExtension.ROLE, DLanguageFacetConstants.ID, DLanguageFacetConstants.NAME) {
            @Override
            protected JpsDLanguageModuleExtension loadExtension(@NotNull final Element facetConfigurationElement, final String name, final JpsElement parent, final JpsModule module) {
                final DLanguageModuleExtensionProperties props = XmlSerializer.deserialize(facetConfigurationElement, DLanguageModuleExtensionProperties.class);
                return new JpsDLanguageModuleExtension(props == null ? new DLanguageModuleExtensionProperties() : props);
            }

            @Override
            protected void saveExtension(final JpsDLanguageModuleExtension extension, final Element facetConfigurationTag, final JpsModule module) {
                XmlSerializer.serializeInto(extension.getProperties(), facetConfigurationTag, new SkipDefaultValuesSerializationFilters());
            }
        });
    }

    @NotNull
    @Override
    public List<? extends JpsProjectExtensionSerializer> getProjectExtensionSerializers() {
        return Collections.singletonList(new JpsDLanguageBuildOptionsSerializer());
    }
}

