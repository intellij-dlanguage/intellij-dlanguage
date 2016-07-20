package net.masterthought.dlanguage.jps.model;

import com.intellij.util.xmlb.annotations.Tag;

/**
 * Serialization object for communicating build settings with the build server.
 */
public class DLanguageBuildOptions {
    public static final String DEFAULT_DMD_PATH = "dmd";
    public static final String DEFAULT_RDMD_PATH = "rdmd";
    public static final String DEFAULT_DUB_PATH = "dub";

    public DLanguageBuildOptions() {
    }

    public DLanguageBuildOptions(DLanguageBuildOptions options) {
        myDmdPath = options.myDmdPath;
        myDubPath = options.myDubPath;
        myrDmdPath = options.myrDmdPath;
    }

    @Tag("dmdPath")
    public String myDmdPath = DEFAULT_DMD_PATH;

    @Tag("rdmdPath")
    public String myrDmdPath = DEFAULT_RDMD_PATH;

    @Tag("dubPath")
        public String myDubPath = DEFAULT_DUB_PATH;

    @Override
    public String toString() {
        return "DLanguageBuildOptions{" +
                "myDmdPath=" + myDmdPath +
                "myDubPath=" + myDubPath +
                ", myrDmdPath=" + myrDmdPath +
                '}';
    }
}

