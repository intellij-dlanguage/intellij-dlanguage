package io.github.intellij.dlanguage.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * String wrapper to ensure that we don't accidentally pass an invalid key to the PropertiesComponent.
 * These are set as property keys in DToolsConfigurable.
 */
public class ToolKey {
    public static final ToolKey DUB_KEY = new ToolKey("dub");
    public static final ToolKey DSCANNER_KEY = new ToolKey("dscanner");
    public static final ToolKey DCD_SERVER_KEY = new ToolKey("dcd-server");
    public static final ToolKey DCD_CLIENT_KEY = new ToolKey("dcd-client");
    public static final ToolKey DFORMAT_KEY = new ToolKey("dfmt");
    public static final ToolKey GDB_KEY = new ToolKey("gdb");

    private final String name;
    private final String pathKey;
    private final String flagsKey;

    private ToolKey(@NotNull final String name) {
        this.name = name;
        this.pathKey = name + "Path";
        this.flagsKey = name + "Flags";
    }

    public String getName() {
        return name;
    }

    public String getPathKey() {
        return pathKey;
    }

    public String getFlagsKey() {
        return flagsKey;
    }

    @Nullable
    public String getPath() {
        final String path = PropertiesComponent.getInstance().getValue(pathKey);
        return path == null || path.isEmpty() ? null : StringUtil.trim(path);
    }

    public void setPath(@Nullable final String newValue) {
        PropertiesComponent.getInstance().setValue(pathKey, StringUtil.trim(newValue));
    }

    @NotNull
    public String getFlags() {
        final String flags = PropertiesComponent.getInstance().getValue(flagsKey);
        return flags == null ? "" : StringUtil.trim(flags);
    }
}

