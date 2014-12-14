package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lander Brandt on 10/24/14.
 */
public class DSdkUtil {

    public static boolean sdkPathIsValid(File root) {
        if (!root.exists()) {
            return false;
        }

        // Check for the following directories as subdirectories of root
        String[] subDirs = {"bin", "include", "lib"};
        for (String dirName : subDirs) {
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
        String compilerBinaryPath = "bin/dmd";
        if (SystemInfo.isWindows) {
            compilerBinaryPath += ".exe";
        }

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
}
