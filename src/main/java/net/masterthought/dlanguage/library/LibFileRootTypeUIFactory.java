package net.masterthought.dlanguage.library;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.ui.SdkPathEditor;
import com.intellij.openapi.roots.ui.OrderRootTypeUIFactory;
import net.masterthought.dlanguage.icons.DLanguageIcons;

import javax.swing.*;

public class LibFileRootTypeUIFactory implements OrderRootTypeUIFactory {
    @Override
    public SdkPathEditor createPathEditor(Sdk sdk)
    {
        return new SdkPathEditor(getNodeText(), LibFileRootType.getInstance(),
                FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
    }

    @Override
    public Icon getIcon()
    {
        return DLanguageIcons.FILE;
    }

    @Override
    public String getNodeText()
    {
        return "External Attributes";
    }
}
