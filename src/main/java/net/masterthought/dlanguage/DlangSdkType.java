package net.masterthought.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import net.masterthought.dlanguage.icons.DlangIcons;
import net.masterthought.dlanguage.library.LibFileRootType;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for the mechanics when pressing "+" in the SDK configuration,
 * as well as the project SDK configuration.
 */
public class DlangSdkType extends SdkType {

    private static final Logger LOG = Logger.getInstance(DlangSdkType.class);

    private static final String SDK_TYPE_ID = "DMD2 SDK";
    private static final String SDK_NAME = "DMD v2 SDK";

    @Nullable private File DEFAULT_DMD_PATH = null;
    @Nullable private File DEFAULT_DOCUMENTATION_PATH = null;
    @Nullable private File DEFAULT_PHOBOS_PATH = null;
    @Nullable private File DEFAULT_DRUNTIME_PATH = null;

    private File dmdBinary = null;

    @NotNull
    public static DlangSdkType getInstance() {
        final DlangSdkType sdkType = SdkType.findInstance(DlangSdkType.class);
        return sdkType != null? sdkType : new DlangSdkType();
    }

    public DlangSdkType() {
        super(SDK_TYPE_ID);

        if(SystemInfo.isWindows) {
            DEFAULT_DMD_PATH = new File("C:/D/dmd2/windows/bin/dmd.exe");
            DEFAULT_DOCUMENTATION_PATH = new File("C:/D/dmd2/html/d");
            DEFAULT_PHOBOS_PATH = new File("C:/D/dmd2/src/phobos");
            DEFAULT_DRUNTIME_PATH = new File("C:/D/dmd2/src/druntime/singleImport");
        } else if(SystemInfo.isMac) {
            DEFAULT_DMD_PATH = new File("/usr/local/opt/dmd"); // correct for Homebrew, standard maybe '/usr/local/bin'
            //DEFAULT_DOCUMENTATION_PATH = new File("");
            DEFAULT_PHOBOS_PATH = new File("/Library/D/dmd/src/phobos");
            DEFAULT_DRUNTIME_PATH = new File("/Library/D/dmd/src/druntime/singleImport");
        } else if(SystemInfo.isLinux) {
            DEFAULT_DMD_PATH = new File("/usr/bin/dmd");
            DEFAULT_DOCUMENTATION_PATH = new File("/usr/share/dmd/html/d");
            DEFAULT_PHOBOS_PATH = new File("/usr/include/dmd/phobos");
            DEFAULT_DRUNTIME_PATH = new File("/usr/include/dmd/druntime/singleImport");
        } else {
            LOG.warn(String.format("We didn't cater for %s", SystemInfo.getOsNameAndVersion()));
        }
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return DlangIcons.FILE;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return DlangIcons.FILE;
    }

    @Nullable
    @Override
    public String suggestHomePath() {
        return DEFAULT_DMD_PATH != null && DEFAULT_DMD_PATH.exists() ? DEFAULT_DMD_PATH.getAbsolutePath() : null;
    }

    /* When user set up DMD SDK path this method checks if specified path contains DMD compiler executable. */
    @Override
    public boolean isValidSdkHome(final String sdkHome) {
        final String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";

        File dmdBinary = new File(sdkHome, executableName);

        if(dmdBinary.exists() && dmdBinary.canExecute()) {
            this.dmdBinary = dmdBinary;
            return true;
        }

        if(SystemInfo.isWindows) {
            final File dmdHome = new File(sdkHome);
            if(dmdHome.exists() && dmdHome.isDirectory()) {
                dmdBinary = Paths.get(sdkHome, "windows", "bin", executableName).toFile(); // C:\D\dmd2\windows\bin\dmd.exe
            }
        }

        if(dmdBinary.exists() && dmdBinary.canExecute()) {
            this.dmdBinary = dmdBinary;
            return true;
        }
        return false;
    }

    @Override
    public String suggestSdkName(final String currentSdkName, final String sdkHome) {
        final String version = getDmdVersion(sdkHome);
        return version != null? version : SDK_NAME;
    }

    /**
     * Windows has docs in 'C:\D\dmd2\html\d' and sources in ['C:\D\dmd2\src\phobos', 'C:\D\dmd2\src\druntime\singleImport']
     * OSX has docs in ??? and sources in ['/Library/D/dmd/src/phobos', '/Library/D/dmd/src/druntime/singleImport']
     * Linux has docs in '/usr/share/dmd/html/d' and sources in ['/usr/include/dmd/phobos', '/usr/include/dmd/druntime/singleImport']
     * @param sdk The DMD installation
     */
    @Override
    public void setupSdkPaths(@NotNull final Sdk sdk) {
        final SdkModificator sdkModificator = sdk.getSdkModificator();

        // documentation paths (todo: find out why using 'OrderRootType.DOCUMENTATION' didn't work)
//        final File docDir = Paths.get(dmdHomePath, "html", "d").toFile();
//        final VirtualFile docs = docDir.isDirectory() ? LocalFileSystem.getInstance().findFileByPath(docDir.getAbsolutePath()) : null;
//        if (docs != null) {
//            sdkModificator.addRoot(docs, OrderRootType.DOCUMENTATION);
//        } else {
//            final VirtualFile fxDocUrl = VirtualFileManager.getInstance().findFileByUrl("http://dlang.org/spec/spec.html");
//            sdkModificator.addRoot(fxDocUrl, OrderRootType.DOCUMENTATION);
//        }

        // add phobos to sources root
        if(DEFAULT_PHOBOS_PATH != null) {
            final VirtualFile phobosSource = DEFAULT_PHOBOS_PATH.isDirectory() ? LocalFileSystem.getInstance().findFileByPath(DEFAULT_PHOBOS_PATH.getAbsolutePath()) : null;
            if (phobosSource != null) {
                sdkModificator.addRoot(phobosSource, OrderRootType.SOURCES);
            }
        }

        // add druntime to sources root
        if(DEFAULT_DRUNTIME_PATH != null) {
            final VirtualFile druntimeSource = DEFAULT_DRUNTIME_PATH.isDirectory() ? LocalFileSystem.getInstance().findFileByPath(DEFAULT_DRUNTIME_PATH.getAbsolutePath()) : null;
            if (druntimeSource != null) {
                sdkModificator.addRoot(druntimeSource, OrderRootType.SOURCES);
            }
        }

        sdkModificator.commitChanges();
    }

    @Nullable
    @Override // takes precedence over getVersionString(String)
    public String getVersionString(@NotNull final Sdk sdk) {
        final String sdkName = sdk.getName();
        return StringUtil.isNotEmpty(sdkName) ? sdkName.substring(sdkName.indexOf('v') + 1) : null;
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull final String sdkHome) {
        final String version = getDmdVersion(sdkHome);

        if(StringUtil.isNotEmpty(version)) {
            final Matcher m = Pattern.compile("(?:.*v)(.+)").matcher(version);
            return m.matches() ? m.group(1) : null;
        }

        return null;
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(final SdkModel sdkModel, final SdkModificator sdkModificator) {
        return null;
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return DlangBundle.INSTANCE.message("compilers.dmd.presentableName");
    }

    @Override
    public void saveAdditionalData(@NotNull final SdkAdditionalData sdkAdditionalData,
                                   @NotNull final Element element) {
        //pass
    }

    @Override
    public boolean isRootTypeApplicable(@NotNull final OrderRootType type) {
        return type != LibFileRootType.getInstance() && super.isRootTypeApplicable(type);
    }

    /**
     * Try to execute 'dmd --version' and return first line of the output.
     * @param sdkHome path to dmd home directory
     * @return String containing DMD version or null
     */
    @Nullable
    private String getDmdVersion(final String sdkHome) {
        if(isValidSdkHome(sdkHome)) {
            final GeneralCommandLine cmd = new GeneralCommandLine();
            //cmd.withWorkDirectory(sdkHome.getAbsolutePath());
            cmd.setExePath(dmdBinary.getAbsolutePath());
            cmd.addParameter("--version");

            try {
                final ProcessOutput output = new CapturingProcessHandler(
                    cmd.createProcess(),
                    Charset.defaultCharset(),
                    cmd.getCommandLineString()
                ).runProcess();

                //Parse output of a DMD compiler
                final List<String> outputLines = output.getStdoutLines();
                if(outputLines != null && !outputLines.isEmpty()) {
                    final String version = outputLines.get(0).trim();
                    LOG.debug(String.format("Found version: %s", version));
                    return version;
                }
            } catch (final ExecutionException e) {
                LOG.error("There was a problem running 'dmd --version'", e);
            }
        }
        return null;
    }

    /* Returns full path to DMD compiler executable */
    public String getDmdPath(@NotNull final Sdk sdk) {
        final String homePath = sdk.getHomePath();

        if(isValidSdkHome(homePath)) {
            return dmdBinary.getAbsolutePath();
        }

        return "dmd"; // it may just be on the PATH
    }

    /* Returns full path to DMD compiler sources */
//    public static String getDmdSourcesPaths(final Sdk sdk) {
//        final String sdkHome = sdk.getHomePath();
//        final String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
//        final File dmdCompilerFile = new File(sdkHome, executableName);
//        return dmdCompilerFile.getAbsolutePath();
//    }
}


