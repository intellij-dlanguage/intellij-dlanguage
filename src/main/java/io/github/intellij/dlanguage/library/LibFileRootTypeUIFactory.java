package io.github.intellij.dlanguage.library;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.ui.SdkPathEditor;
import com.intellij.openapi.roots.ui.OrderRootTypeUIFactory;
import io.github.intellij.dlanguage.DLanguage;

import javax.swing.*;

public class LibFileRootTypeUIFactory implements OrderRootTypeUIFactory {
    @Override
    public SdkPathEditor createPathEditor(final Sdk sdk) {
        return new SdkPathEditor(getNodeText(), LibFileRootType.getInstance(),
            FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
    }

    @Override
    public Icon getIcon() {
        return DLanguage.Icons.FILE;
    }

    @Override
    public String getNodeText() {
        return "External Attributes";
    }
}
