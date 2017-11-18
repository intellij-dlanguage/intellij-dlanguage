package io.github.intellij.dlanguage.icons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class DlangIcons {
    public static final Icon FILE = IconLoader.getIcon("/icons/d.png");
    public static final Icon MODULE = FILE;
    public static final Icon SDK = FILE;
    public static final Icon SDK_ADD = FILE;
    public static final Icon RUN = FILE;
    public static final Icon LIBRARY = FILE;

    public static final Icon CLASS = FILE;

    public static final Icon NODE_CLASS = AllIcons.Nodes.Class;
    public static final Icon NODE_INTERFACE = AllIcons.Nodes.Interface;
    public static final Icon NODE_ENUM = AllIcons.Nodes.Enum;
    public static final Icon NODE_FUNCTION = IconLoader.getIcon("/icons/nodes/function.png");
    public static final Icon NODE_METHOD = AllIcons.Nodes.Method;
}
