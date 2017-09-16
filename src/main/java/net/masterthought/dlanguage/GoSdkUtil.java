package net.masterthought.dlanguage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;

import java.util.Map;

public class GoSdkUtil {
    public final static String defualtBuilderArguments = "--build=debug";
    private static boolean hostOsWindows = false;

    public static Sdk getGoogleGoSdkForProject(Project project) {
        return null;
    }

    public static Map<String, String> getExtendedSysEnv(GoSdkData sdkData, String projectDir, String envVars) {
        return null;
    }

    public static String[] convertEnvMapToArray(Map<String, String> sysEnv) {
        return new String[0];
    }

    public static boolean isHostOsWindows() {
        return hostOsWindows;
    }

    public static String[] computeGoBuildCommand(String goExecName, String builderArguments, String execName, String scriptName) {
        return new String[0];
    }

    public static boolean checkFileExists(String pythonRuntime) {
        return false;
    }

    public static String[] getExtendedGoEnv(GoSdkData sdkData, String projectDir, String s) {
        return new String[0];
    }
}
