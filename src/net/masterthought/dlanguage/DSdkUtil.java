package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DSdkUtil {

    private static  final String[] SDK_EXPECTED_SUBDIRS = {"bin", "include", "lib"};

    public static boolean sdkPathIsValid(File root) {
        if (!root.exists()) {
            return false;
        }

        // Check for the following directories as subdirectories of root
        for (String dirName : SDK_EXPECTED_SUBDIRS) {
            File subDirectory = new File(FileUtil.join(root.getPath(), dirName));
            if (!subDirectory.exists()) {
                return false;
            }
        }

        return true;
    }

    public static String getSdkVersion(String homePath) {
        // Optionally, just traverse the directory tree but that's not reliable and I have no idea if it's like
        // that for non-homebrew installs
        Pattern pattern = Pattern.compile("Compiler v(\\d+\\.\\d*)");

        // TODO: Support GDMD and other crap
        String compilerBinaryPath = "bin/" + getCompilerExecutableFileName();

        File compilerBinary = new File(FileUtil.join(homePath, compilerBinaryPath));
        if (!compilerBinary.exists()) {
            return null;
        }

        GeneralCommandLine command = new GeneralCommandLine();
        command.setExePath(compilerBinary.getAbsolutePath());


        try {
            ProcessOutput output = new CapturingProcessHandler(command.createProcess(),
                    Charset.defaultCharset(),
                    command.getCommandLineString()).runProcess();

            if (output.getExitCode() != 0) {
                Matcher m = pattern.matcher(output.getStdout());

                if (m.find()) {
                    return m.group();
                }

                return null;
            } else {
                // TODO: Log exit code
            }

        } catch(ExecutionException e) {
            // TODO: Log error
            return null;
        }

        return null;
    }

    public static File getCompilerExecutable(@NotNull String SDKPath) {
        File SDKfolder = new File(SDKPath);
        File binaryFolder = new File(SDKfolder, "bin");
        return new File(binaryFolder, getCompilerExecutableFileName());
    }

    public static String getCompilerExecutableFileName() {
        return SystemInfo.isWindows ? "dmd.exe" : "dmd";
    }
}
