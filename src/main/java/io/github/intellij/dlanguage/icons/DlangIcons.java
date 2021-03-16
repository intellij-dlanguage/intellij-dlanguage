package io.github.intellij.dlanguage.icons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;

import javax.swing.*;

public class DlangIcons {
    public static final Icon FILE = IconLoader.getIcon("/icons/d.png", DlangIcons.class);
    public static final Icon MODULE = FILE;
    public static final Icon SDK = FILE;
    public static final Icon SDK_ADD = FILE;
    public static final Icon RUN = FILE;
    public static final Icon LIBRARY = FILE;

    public static final Icon SRC_FILE = IconLoader.getIcon("/icons/d-file.png", DlangIcons.class);
    public static final Icon SRC_FILE_RUNNABLE = new LayeredIcon(DlangIcons.SRC_FILE, AllIcons.Nodes.RunnableMark);
    public static final Icon SRC_FILE_PACKAGE = IconLoader.getIcon("/icons/d-file-package.png", DlangIcons.class);

    public static final Icon CLASS = FILE;

    public static final Icon NODE_CLASS = AllIcons.Nodes.Class;
    public static final Icon NODE_INTERFACE = AllIcons.Nodes.Interface;
    public static final Icon NODE_STRUCT = IconLoader.getIcon("/icons/nodes/struct.png", DlangIcons.class);
    public static final Icon NODE_UNION = IconLoader.getIcon("/icons/nodes/union.png", DlangIcons.class);
    public static final Icon NODE_ENUM = AllIcons.Nodes.Enum;
    public static final Icon NODE_FUNCTION = IconLoader.getIcon("/icons/nodes/function.png", DlangIcons.class);
    public static final Icon NODE_METHOD = AllIcons.Nodes.Method;
    public static final Icon NODE_FIELD = AllIcons.Nodes.Field;
    public static final Icon NODE_PROPERTY = AllIcons.Nodes.Property;
    public static final Icon NODE_PROPERTY_GETTER = IconLoader.getIcon("/icons/nodes/propertyGetter.png", DlangIcons.class);
    public static final Icon NODE_PROPERTY_SETTER = IconLoader.getIcon("/icons/nodes/propertySetter.png", DlangIcons.class);
    public static final Icon NODE_ALIAS = IconLoader.getIcon("/icons/nodes/alias.png", DlangIcons.class);
    public static final Icon NODE_MIXIN = IconLoader.getIcon("/icons/nodes/mixin.png", DlangIcons.class);
}
