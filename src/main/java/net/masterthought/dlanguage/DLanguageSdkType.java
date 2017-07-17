package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
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

    private static final Logger LOG = Logger.getInstance(DLanguageSdkType.class);

    private static final String SDK_TYPE_ID = "DMD2 SDK";
    private static final String SDK_NAME = "DMD v2 SDK";

    private static final File DEFAULT_SDK_PATH_WINDOWS = new File("c:/D/DMD2/windows/");
    private static final File DEFAULT_SDK_PATH_OSX = new File("/usr/local/opt/dmd");
    private static final File DEFAULT_SDK_PATH_LINUX = new File("/usr/bin");

    public DLanguageSdkType() {
        super(SDK_TYPE_ID);
    }

    @NotNull
    public static DLanguageSdkType getInstance() {
        final DLanguageSdkType sdkType = SdkType.findInstance(DLanguageSdkType.class);
        return sdkType != null ? sdkType : new DLanguageSdkType();
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
    public String suggestSdkName(String currentSdkName, String sdkHome) {
        final String version = getDmdVersion(sdkHome);
        return version != null ? version : SDK_NAME;
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull String sdkHome) {
        final String version = getDmdVersion(sdkHome);

        if (version != null) {
            final Matcher m = Pattern.compile(".*v(\\d+\\.\\d+).*").matcher(version);
            if (m.matches()) {
                return m.group(1);
            }
        }

        return "2.0";
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

    /**
     * Try to execute 'dmd --version' and return first line of the output.
     *
     * @param sdkHome path to dmd home directory
     * @return String containing DMD version or null
     */
    @Nullable
    private String getDmdVersion(final String sdkHome) {
        final File compilerFolder = new File(sdkHome);
        final File compilerFile = new File(sdkHome, SystemInfo.isWindows ? "dmd.exe" : "dmd");

        final GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.withWorkDirectory(compilerFolder.getAbsolutePath());
        commandLine.setExePath(compilerFile.getAbsolutePath());
        commandLine.addParameter("--version");

        try {
            final ProcessOutput output = new CapturingProcessHandler(
                commandLine.createProcess(),
                Charset.defaultCharset(),
                commandLine.getCommandLineString()
            ).runProcess();

            //Parse output of a DMD compiler
            final List<String> outputLines = output.getStdoutLines();
            if (outputLines != null && !outputLines.isEmpty()) {
                final String version = outputLines.get(0).trim();
                LOG.debug(String.format("Found version: %s", version));
                return version;
            }
        } catch (final ExecutionException e) {
            return null;
        }
        return null;
    }
}


