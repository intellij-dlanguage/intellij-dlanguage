package uk.co.cwspencer.ideagdb.debug.utils;

import com.intellij.openapi.util.SystemInfo;

public class SdkUtil {
    public static boolean isHostOsWindows() {
        return SystemInfo.isWindows;
    }
}
