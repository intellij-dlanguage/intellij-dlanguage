package io.github.intellij.dlanguage.jps.model;

import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Tag;
import com.intellij.util.xmlb.annotations.XCollection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DLanguageModuleExtensionProperties {
    @Tag("parseTransforms")
    @XCollection(style = XCollection.Style.v1, elementName = "transform")
    public List<String> myParseTransforms = ContainerUtil.newArrayList(); //should not contain duplicate elements

    public DLanguageModuleExtensionProperties() {
    }

    public DLanguageModuleExtensionProperties(@NotNull final DLanguageModuleExtensionProperties props) {
        myParseTransforms = ContainerUtil.newArrayList(props.myParseTransforms);
    }
}
