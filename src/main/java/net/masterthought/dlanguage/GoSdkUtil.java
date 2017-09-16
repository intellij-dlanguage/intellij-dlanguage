package net.masterthought.dlanguage;

public class GoSdkUtil {
    public final static String defualtBuilderArguments = "--build=debug";
    private static boolean hostOsWindows = false;

    public static boolean isHostOsWindows() {
        return hostOsWindows;
    }

    public static String[] computeGoBuildCommand(String goExecName, String builderArguments, String execName, String scriptName) {
        return new String[0];
    }

}
