package net.masterthought.dlanguage.settings;

public class ToolSettings {
    private final String path;
    private final String flags;

    public ToolSettings(String path, String flags) {
        this.path = path;
        this.flags = flags;
    }

    public String getPath() {
        return path;
    }

    public String getFlags() {
        return flags;
    }
}
