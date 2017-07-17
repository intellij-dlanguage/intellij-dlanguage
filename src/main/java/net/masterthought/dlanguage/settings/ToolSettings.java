package net.masterthought.dlanguage.settings;

public class ToolSettings {
    private String path;
    private String flags;

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
