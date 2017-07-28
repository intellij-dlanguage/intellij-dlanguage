package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DSdkUtil {

    private static final Logger LOG = Logger.getInstance(DSdkUtil.class);

    private static final String[] SDK_EXPECTED_SUBDIRS = {"bin", "include", "lib"};

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
        String output = runCompiler(command);

        if (output != null) {
            Matcher m = pattern.matcher(output);
            if (m.find()) {
                return m.group();
            }
        }

        return null;
    }

    private static String runCompiler(GeneralCommandLine command) {
        try {
            ProcessOutput output = new CapturingProcessHandler(command.createProcess(),
                Charset.defaultCharset(),
                command.getCommandLineString()).runProcess();

            if (output.getExitCode() != 0) {
                return output.getStdout();
            } else {
                LOG.warn("compiler returned exit code " + output.getExitCode());
            }

        } catch (ExecutionException e) {
            LOG.warn("compiler execution failed. " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets a file reference to compiler executable
     *
     * @param SDKPath path to SDK
     * @return File reference to compiler executable
     */
    public static File getCompilerExecutable(@NotNull String SDKPath) {
        File SDKfolder = new File(SDKPath);
        File binaryFolder = new File(SDKfolder, "bin");
        return new File(binaryFolder, getCompilerExecutableFileName());
    }

    /**
     * Gets the filename for compiler executable (result is OS specific)
     *
     * @return Compiler executable filename
     */
    public static String getCompilerExecutableFileName() {
        return SystemInfo.isWindows ? "dmd.exe" : "dmd";
    }
}
