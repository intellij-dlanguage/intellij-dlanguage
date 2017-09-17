package uk.co.cwspencer.ideagdb.debug.go;

import com.intellij.openapi.util.SystemInfo;

public class GoSdkUtil {
    public static boolean isHostOsWindows() {
        return SystemInfo.isWindows;
    }
}
