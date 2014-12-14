package net.masterthought.dlanguage.jps.model;

import com.intellij.util.xmlb.annotations.Tag;

/**
 * Serialization object for communicating build settings with the build server.
 */
public class DLanguageBuildOptions {
    public static final String DEFAULT_DMD_PATH = "dmd";

    public DLanguageBuildOptions() {
    }

    public DLanguageBuildOptions(DLanguageBuildOptions options) {
        myDmdPath = options.myDmdPath;
    }

    @Tag("dmdPath")
    public String myDmdPath = DEFAULT_DMD_PATH;


    @Override
    public String toString() {
        return "DLanguageBuildOptions{" +
                "myDmdPath=" + myDmdPath +
                '}';
    }
}

