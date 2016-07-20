package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.SystemInfo;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.library.LibFileRootType;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for the mechanics when pressing "+" in the SDK configuration,
 * as well as the project SDK configuration.
 */
public class DLanguageSdkType extends SdkType {
    public static final String SDK_TYPE_ID = "DMD2 SDK";
    public static final String SDK_NAME = "DMD v2 SDK";

    public static final File DEFAULT_SDK_PATH_WINDOWS = new File("c:/D/DMD2/windows/");
       public static final File DEFAULT_SDK_PATH_OSX = new File("/usr/local/opt/dmd");
       public static final File DEFAULT_SDK_PATH_LINUX = new File("/usr/");

    @NotNull
    public static DLanguageSdkType getInstance() {
        DLanguageSdkType sdkType = SdkType.findInstance(DLanguageSdkType.class);
        if(sdkType == null) {
            return new DLanguageSdkType();
        }
        else {
            return sdkType;
        }
    }

    public DLanguageSdkType() {
        super(SDK_TYPE_ID);
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return DLanguageIcons.FILE;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

    @Nullable
    @Override
    public String suggestHomePath() {
        if (SystemInfo.isWindows) {
            if (DEFAULT_SDK_PATH_WINDOWS.exists()) {
                return DEFAULT_SDK_PATH_WINDOWS.getAbsolutePath();
            }
        } else if (SystemInfo.isMac) {
            if (DEFAULT_SDK_PATH_OSX.exists()) {
                return DEFAULT_SDK_PATH_OSX.getAbsolutePath();
            }
        } else if (SystemInfo.isLinux) {
            if (DEFAULT_SDK_PATH_LINUX.exists()) {
                return DEFAULT_SDK_PATH_LINUX.getAbsolutePath();
            }
        }
        return null;
    }

    /* When user set up DMD SDK path this method checks if specified path contains DMD compiler executable. */
    @Override
    public boolean isValidSdkHome(String sdkHome) {
        String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
        File dmdCompilerFile = new File(sdkHome, executableName);
        return dmdCompilerFile.canExecute();
    }

    @Override
    public String suggestSdkName(String s, String sdkHome) {
        String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
        String guessedVersion = getSdkVersion(sdkHome, executableName);
        if(guessedVersion == null) {
            return SDK_NAME;
        }
        else {
            return "DMD v"+guessedVersion+" SDK";
        }
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull String sdkHome) {
        String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
        String guessedVersion = getSdkVersion(sdkHome, executableName);
        return (guessedVersion!=null) ? guessedVersion : "2.0";
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(SdkModel sdkModel, SdkModificator sdkModificator) {
        return null;
    }

    @Override
    public String getPresentableName() {
        return "Digital Mars D compiler";
    }

    @Override
    public void saveAdditionalData(@NotNull SdkAdditionalData sdkAdditionalData, @NotNull Element element) {
        //pass
    }

    @Override
    public boolean isRootTypeApplicable(OrderRootType type) {
        return type != LibFileRootType.getInstance() && super.isRootTypeApplicable(type);
    }

    /* Try to execute DMD compiler and parse first line to get a version
     * @returns String with DMD version or null in case of any error. */
    @Nullable
    public String getSdkVersion(String sdkHome, String executableName) {
        File compilerFolder = new File(sdkHome);
        File compilerFile = new File(sdkHome, executableName);

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.withWorkDirectory(compilerFolder.getAbsolutePath());
        commandLine.setExePath(compilerFile.getAbsolutePath());

        ProcessOutput output = null;
        try {
            output = new CapturingProcessHandler(commandLine.createProcess(), Charset.defaultCharset(),
                    commandLine.getCommandLineString()).runProcess();
        } catch (ExecutionException e) {
            return null;
        }

        //Parse output of a DMD compiler
        List<String> outputLines = output.getStdoutLines();
        if(outputLines.size()>0) {
            String firstLine = outputLines.get(0).trim(); // line in format: "DMD32 D Compiler v2.058"
            Pattern pattern = Pattern.compile(".*v(\\d+\\.\\d+).*");
            Matcher m = pattern.matcher(firstLine);
            if(m.matches()) {
                return m.group(1);
            }
        }

        return null;
    }

    /* Returns full path to DMD compiler executable */
    public static String getDmdPath(Sdk sdk) {
        String sdkHome = sdk.getHomePath();
        String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
        File dmdCompilerFile = new File(sdkHome, executableName);
        return dmdCompilerFile.getAbsolutePath();
    }

    /* Returns full path to DMD compiler sources */
    public static String getDmdSourcesPaths(Sdk sdk) {
        String sdkHome = sdk.getHomePath();
        String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
        File dmdCompilerFile = new File(sdkHome, executableName);
        return dmdCompilerFile.getAbsolutePath();
    }
}


