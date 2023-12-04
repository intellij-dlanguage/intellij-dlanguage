package io.github.intellij.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.library.LibFileRootType;
import java.io.IOException;
import java.nio.file.Files;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Responsible for the mechanics when pressing "+" in the SDK configuration, as well as the project
 * SDK configuration.
 */
public class DlangSdkType extends SdkType {

    private static final Logger LOG = Logger.getInstance(DlangSdkType.class);

    public static final String SDK_TYPE_ID = "DMD2 SDK";
    private static final String SDK_NAME = "DMD v2 SDK";

    @NotNull
    private static final File[] DEFAULT_DMD_PATHS;
    @NotNull
    private static final File[] DEFAULT_DOCUMENTATION_PATHS;
    @NotNull
    private static final File[] DEFAULT_PHOBOS_PATHS;
    @NotNull
    private static final File[] DEFAULT_DRUNTIME_PATHS;

    static {
        if (SystemInfo.isWindows) {
            // This covers the default paths used by the Windows installer and therefore
            // https://chocolatey.org/packages/dmd is also covered (as it uses same process).
            DEFAULT_DMD_PATHS = new File[] {
                new File("C:/D/dmd2/windows/bin/dmd.exe")
            };
            DEFAULT_DOCUMENTATION_PATHS = new File[] {
                new File("C:/D/dmd2/html/d")
            };
            DEFAULT_PHOBOS_PATHS = new File[] {
                new File("C:/D/dmd2/src/phobos")
            };
            DEFAULT_DRUNTIME_PATHS = new File[] {
                new File("C:/D/dmd2/src/druntime/import")
            };
        } else if (SystemInfo.isMac) {
            // I use Homebrew on Mac. These paths may differ from what the .dmg does.
            DEFAULT_DMD_PATHS = new File[] {
                new File("/usr/local/opt/dmd"), // installed via Homebrew
                new File("/usr/local/bin"), // dmg install
                new File("/opt/local/bin")
                };
            DEFAULT_DOCUMENTATION_PATHS = new File[]{};
            DEFAULT_PHOBOS_PATHS = new File[] {
                new File("/Library/D/dmd/src/phobos") // installed via Homebrew
            };
            DEFAULT_DRUNTIME_PATHS = new File[] {
                new File("/Library/D/dmd/src/druntime/import") // installed via Homebrew
            };
        } else if (SystemInfo.isUnix) {
            // The official .rpm and .deb installers are the priority
            DEFAULT_DMD_PATHS = new File[] {
                new File("/usr/bin"), // Debian, Ubuntu & Fedora (official .rpm), note that Arch also uses this path
                new File("/usr/local/bin"),
                new File("/snap/bin/dmd") // snapcraft.io (symlink to /snap/dmd/current/bin/dmd)
            };
            // the path to D documentation should contain "index.html"
            DEFAULT_DOCUMENTATION_PATHS = new File[] {
                new File("/usr/share/dmd/html/d"), // Fedora (official .rpm)
                new File("/usr/local/share/dmd/html/d"), // Ubuntu
                new File("/usr/share/d/html/d") // Arch Linux (dmd-docs package is not consistent with dlang & dlang-dmd package paths)
            };
            // the path to phobos should contain "etc.c.*" and "std.*"
            DEFAULT_PHOBOS_PATHS = new File[] {
                new File("/usr/include/dmd/phobos"), // Fedora (official .rpm)
                new File("/usr/local/include/dmd/phobos"), // Ubuntu (should it be src/phobos?)
                new File("/usr/include/dlang/dmd"), // Arch Linux uses non-standard directory structure (see: #457 and #743)
                new File("/snap/dmd/current/import/phobos") // snapcraft.io
            };
            // the path to phobos should contain "core.*", "importc.h", and "object.d"
            DEFAULT_DRUNTIME_PATHS = new File[] {
                new File("/usr/include/dmd/druntime/import"), // Fedora (official .rpm)
                new File("/usr/local/include/dmd/druntime/import"), // Ubuntu (should it be src/druntime/import?)
                new File("/usr/include/dlang/dmd"), // Arch Linux uses non-standard directory structure (see: #457 and #743)
                new File("/snap/dmd/current/import/druntime") // snapcraft.io
            };
        } else {
            LOG.warn(String.format("We didn't cater for %s", SystemInfo.getOsNameAndVersion()));
            DEFAULT_DMD_PATHS = new File[]{};
            DEFAULT_DOCUMENTATION_PATHS = new File[]{};
            DEFAULT_PHOBOS_PATHS = new File[]{};
            DEFAULT_DRUNTIME_PATHS = new File[]{};
        }
    }

    private File dmdBinary = null;

    @NotNull
    public static DlangSdkType getInstance() {
        return SdkType.findInstance(DlangSdkType.class);
    }

    public DlangSdkType() {
        super(SDK_TYPE_ID);
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return DLanguage.Icons.FILE;
    }

    @Nullable
    @Override
    public String suggestHomePath() {
        File found = null;
        for (final File f : DEFAULT_DMD_PATHS) {
            if (f.exists()) {
                if (isValidSdkHome(f.getPath())) {
                  found = f;
                  break;
                } else if (found == null) {
                  found = f;
                }
            }
        }
        return found == null ? null : found.getAbsolutePath();
    }

    /**
     * When user set up DMD SDK path this method checks if specified path contains DMD compiler executable.
     *
     * This method determines if it can run the dmd executable based on a home path that's passed in. So in the case
     * that the sdk home is "C:\D\dmd2\" then this method would return true if "C:\D\dmd2\windows\bin\dmd.exe" exists
     * and is executable.
     *
     * @param sdkHome path to the root directory of a dmd installation
     * @return true if the sdk home contains a executable dmd compiler
     */
    @Override
    public boolean isValidSdkHome(final String sdkHome) {
        final String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";

        File dmdBinary = new File(sdkHome, executableName);

        if (dmdBinary.exists() && dmdBinary.canExecute()) {
            this.dmdBinary = dmdBinary;
            return true;
        }

        if (SystemInfo.isWindows) {
            final File dmdHome = new File(sdkHome);
            if (dmdHome.exists() && dmdHome.isDirectory()) {
                dmdBinary = Paths.get(sdkHome, "windows", "bin", executableName)
                    .toFile(); // C:\D\dmd2\windows\bin\dmd.exe
            }
        }

        if (dmdBinary.exists() && dmdBinary.canExecute()) {
            this.dmdBinary = dmdBinary;
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public String suggestSdkName(@Nullable final String currentSdkName, final String sdkHome) {
        try {
            final String version = Objects.requireNonNull(getDmdVersion(sdkHome)).get(2L, TimeUnit.SECONDS);

            return StringUtil.isNotEmpty(version) ? version : SDK_NAME;
        } catch (InterruptedException | TimeoutException | java.util.concurrent.ExecutionException e) {
            LOG.error("unable to run dmd --version", e);
        }
        return SDK_NAME;
    }

    @Nullable
    @Override
    public String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
        return "https://dlang.org/";
    }

    @Nullable
    @Override
    public String getDownloadSdkUrl() {
        return "https://dlang.org/download.html";
    }

    @Override
    public boolean supportsCustomCreateUI() {
        return false; // if true a call is made to this::showCustomCreateUI()
    }

//    @Override
//    public void showCustomCreateUI(@NotNull SdkModel sdkModel,
//                                   @NotNull JComponent parentComponent,
//                                   @Nullable Sdk selectedSdk,
//                                   @NotNull Consumer<Sdk> sdkCreatedCallback) {
//        LOG.info("attempt to display custom UI for creating D sdk");
//
//        SdkConfigurationUtil.selectSdkHome(this, home -> {
//            final String newSdkName = SdkConfigurationUtil.createUniqueSdkName(this, home, Arrays.asList(sdkModel.getSdks()));
//
//            final Sdk sdk = DlangSdkType.findOrCreateSdk();
//            //final Sdk sdk = new io.github.intellij.dlanguage.settings.DlangCompiler(newSdkName, home, "???");
//
//            sdkCreatedCallback.consume(sdk);
//        });
//    }

    static class SetupStatus {
        private boolean runtime;
        private boolean phobos;
        private boolean documentation;

        SetupStatus(final boolean runtime, final boolean phobos, final boolean documentation) {
            this.runtime = runtime;
            this.phobos = phobos;
            this.documentation = documentation;
        }

        boolean getRuntimeStatus() {
            return runtime;
        }

        boolean getPhobosStatus() {
            return phobos;
        }

        boolean getDocumentationStatus() {
            return documentation;
        }

        public void setRuntime(final boolean runtime) {
            this.runtime = runtime;
        }

        public void setPhobos(final boolean phobos) {
            this.phobos = phobos;
        }

        public void setDocumentation(final boolean documentation) {
            this.documentation = documentation;
        }
    }

    /**
     * Windows has docs in 'C:\D\dmd2\html\d' and sources in ['C:\D\dmd2\src\phobos',
     * 'C:\D\dmd2\src\druntime\import'] OSX has docs in ??? and sources in
     * ['/Library/D/dmd/src/phobos', '/Library/D/dmd/src/druntime/import'] Linux has docs in
     * '/usr/share/dmd/html/d' and sources in ['/usr/include/dmd/phobos',
     * '/usr/include/dmd/druntime/import']
     *
     * @param sdk The DMD installation
     */
    @Override
    public void setupSdkPaths(@NotNull final Sdk sdk) {
        final SdkModificator sdkModificator = sdk.getSdkModificator();

        SetupStatus status = new SetupStatus(false, false, false);

        if (SystemInfo.isWindows) {
            status = setupSDKPathsFromWindowsConfigFile(sdk,sdkModificator);
        }

        // documentation paths (todo: find out why using 'OrderRootType.DOCUMENTATION' didn't work)
        if (!status.getDocumentationStatus()) {
            setupDocumentationPath(sdk, sdkModificator, status);
        }

        // add phobos to sources root
        if (!status.getPhobosStatus()) {
            setupPhobosPaths(sdkModificator, status);
        }

        // add druntime to sources root
        if (!status.getRuntimeStatus()) {
            setupRuntimePaths(sdkModificator, status);
        }

        sdkModificator.commitChanges();
    }

    private Optional<VirtualFile> firstVirtualFileFrom(File[] files) {
        for (File f: files) {
            if (f.isDirectory()) {
                return Optional.ofNullable(
                    LocalFileSystem.getInstance().findFileByPath(f.getAbsolutePath()));
            }
        }
        return Optional.empty();
    }

    private void setupRuntimePaths(final SdkModificator sdkModificator, final SetupStatus status) {
        Optional<VirtualFile> druntimeSource = firstVirtualFileFrom(DEFAULT_DRUNTIME_PATHS);
        if (druntimeSource.isPresent()) {
            sdkModificator.addRoot(druntimeSource.get(), OrderRootType.SOURCES);
            status.setRuntime(true);
        }
        else {
            status.setRuntime(false);
        }
    }

    private void setupPhobosPaths(final SdkModificator sdkModificator, final SetupStatus status) {
        final Optional<VirtualFile> phobosSource = firstVirtualFileFrom(DEFAULT_PHOBOS_PATHS);
        if (phobosSource.isPresent()) {
            sdkModificator.addRoot(phobosSource.get(), OrderRootType.SOURCES);
            status.setPhobos(true);
        }
        else {
            status.setPhobos(false);
        }
    }

    private void setupDocumentationPath(@NotNull final Sdk sdk, final SdkModificator sdkModificator, final SetupStatus status) {
//        final File docDir = DEFAULT_DOCUMENTATION_PATH;//Paths.get(getDmdPath(sdk), "html", "d").toFile();
//        final VirtualFile docs = docDir != null && docDir.isDirectory() ? LocalFileSystem.getInstance().findFileByPath(docDir.getAbsolutePath()) : null;
//        if (docs != null) {
//            sdkModificator.addRoot(docs, OrderRootType.DOCUMENTATION);
//        } else {
//            final VirtualFile fxDocUrl = VirtualFileManager.getInstance().findFileByUrl("https://dlang.org/spec/spec.html");
//            if(fxDocUrl == null){
//                status.setDocumentation(false);
//                return;
//            }
//            sdkModificator.addRoot(fxDocUrl, OrderRootType.DOCUMENTATION);
//        }
        status.setDocumentation(true);//todo adding documentation doesn't work but its not clear why

    }

    @NotNull
    private SetupStatus setupSDKPathsFromWindowsConfigFile(final Sdk sdk, final SdkModificator sdkModificator) {
        final GeneralCommandLine cmd = new GeneralCommandLine(getDmdPath(sdk));

        try {
            final ProcessOutput output = ApplicationManager.getApplication()
                .executeOnPooledThread(() -> new CapturingProcessHandler(
                    cmd.createProcess(),
                    Charset.defaultCharset(),
                    cmd.getCommandLineString()
                ).runProcess(6_000))
                .get();

            final Optional<String> configFile = output
                .getStdoutLines().stream()
                .filter(line -> line.contains("Config file: "))
                .map(line -> line.replace("Config file:", "").trim())
                .findFirst();

            if (configFile.isEmpty()) {
                return new SetupStatus(false, false, false);
            }
            final File file = new File(configFile.get());
            if (!file.exists()) {
                return new SetupStatus(false, false, false);
            }
            //DFLAGS="-I%@P%\..\..\src\phobos" "-I%@P%\..\..\src\druntime\import"
            final String[] phobos = new String[1];
            final Pattern phobosPattern = Pattern
                .compile("\"-I%@P%([\\.\\\\A-Za-z]+phobos[\\.\\\\A-Za-z]*)\"");

            final String[] druntime = new String[1];
            final Pattern druntimePattern = Pattern
                .compile("\"-I%@P%([\\.\\\\A-Za-z]+druntime\\\\import[\\.\\\\A-Za-z]*)\"");

            Files.lines(file.toPath()).forEach(line -> {
                if (line.contains("DFLAGS=")) {
                    final Matcher phobosMatcher = phobosPattern.matcher(line);
                    final Matcher druntimeMatcher = druntimePattern.matcher(line);
                    if(phobosMatcher.find()){
                        phobos[0] = phobosMatcher.group(1);
                    }
                    if(druntimeMatcher.find()){
                        druntime[0] = druntimeMatcher.group(1);
                    }
                }
            });
            final String phobosPath = (new File(getDmdPath(sdk))).getParent() + phobos[0];
            final String druntimePath = (new File(getDmdPath(sdk))).getParent() + druntime[0];
            final File phobosFile = new File(phobosPath);
            final File druntimeFile = new File(druntimePath);
            if (phobosFile.exists() && druntimeFile.exists()) {
                final VirtualFile phobosVirtualFile = LocalFileSystem.getInstance().findFileByPath(phobosFile.getAbsolutePath());
                final VirtualFile druntimeVirtualFile = LocalFileSystem.getInstance().findFileByPath(druntimeFile.getAbsolutePath());
                if(phobosVirtualFile == null || druntimeVirtualFile == null) return new SetupStatus(false, false, false);
                sdkModificator.addRoot(phobosVirtualFile, OrderRootType.SOURCES);
                sdkModificator.addRoot(druntimeVirtualFile, OrderRootType.SOURCES);
                return new SetupStatus(true, true, false);
            } else {
                return new SetupStatus(false, false, false);
            }

        } catch (final IOException | InterruptedException | java.util.concurrent.ExecutionException e) {
            Logger.getInstance(DlangSdkType.class).error(e);
            return new SetupStatus(false, false, false);
        }
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
        try {
            final String version = Objects.requireNonNull(getDmdVersion(sdkHome)).get(2000, TimeUnit.SECONDS);

            if (StringUtil.isNotEmpty(version)) {
                final Matcher m = Pattern.compile("(?:.*v)(.+)").matcher(version);
                return m.matches() ? m.group(1) : null;
            }
        } catch (InterruptedException | TimeoutException | java.util.concurrent.ExecutionException e) {
            LOG.error("unable to run dmd --version", e);
        }

        return null;
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(
                                            @NotNull final SdkModel sdkModel,
                                            @NotNull final SdkModificator sdkModificator) {
        return null; // it's ok to return null but would be good to see what we could do with this
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
    public boolean allowWslSdkForLocalProject() {
        return true;
    }

    @Override
    public boolean isRootTypeApplicable(@NotNull final OrderRootType type) {
        return type != LibFileRootType.getInstance() && super.isRootTypeApplicable(type);
    }

    /**
     * Try to execute 'dmd --version' and return first line of the output.
     *
     * @param sdkHome path to dmd home directory
     * @return String containing DMD version or null
     */
    @NotNull
    private Future<String> getDmdVersion(final String sdkHome) {
        return ApplicationManager.getApplication().executeOnPooledThread(() -> {
            if (isValidSdkHome(sdkHome)) {
                final GeneralCommandLine cmd = new GeneralCommandLine();
                //cmd.withWorkDirectory(sdkHome.getAbsolutePath());
                cmd.setExePath(dmdBinary.getAbsolutePath());
                cmd.addParameter("--version");

                try {
                    final ProcessOutput output = new CapturingProcessHandler(
                        cmd.createProcess(),
                        Charset.defaultCharset(),
                        cmd.getCommandLineString()
                    ).runProcess(2_000 );

                    //Parse output of a DMD compiler
                    final List<String> outputLines = output.getStdoutLines();
                    if (!outputLines.isEmpty()) {
                        final String version = outputLines.get(0).trim();
                        LOG.debug(String.format("Found version: %s", version));
                        return version;
                    }
                } catch (final ExecutionException e) {
                    LOG.error("There was a problem running 'dmd --version'", e);
                }
            }
            return null;
        });
    }

    /**
     * Returns full path to DMD compiler executable for the given Sdk (based on the home path).
     * If the Sdk doesn't have a valid value then this method will simply return "dmd" in the hope that it's on the PATH
     * @param sdk an Sdk of {@link DlangSdkType}
     * @return Either the absolute path to the dmd compiler or simply "dmd"
     */
    public String getDmdPath(@NotNull final Sdk sdk) {
        final String homePath = sdk.getHomePath();

        if (isValidSdkHome(homePath)) {
            return dmdBinary.getAbsolutePath();
        }

        LOG.warn(String.format("Home path '%s' for dlang sdk was not valid. Falling back to 'dmd'", homePath));

        return "dmd"; // it may just be on the PATH
    }

    /* Returns full path to DMD compiler sources */
//    public static String getDmdSourcesPaths(final Sdk sdk) {
//        final String sdkHome = sdk.getHomePath();
//        final String executableName = SystemInfo.isWindows ? "dmd.exe" : "dmd";
//        final File dmdCompilerFile = new File(sdkHome, executableName);
//        return dmdCompilerFile.getAbsolutePath();
//    }

    public static Sdk findOrCreateSdk() {
        final DlangSdkType sdkType = DlangSdkType.getInstance();

        final Comparator<Sdk> sdkComparator = (sdk1, sdk2) -> {
            if (sdk1.getSdkType() == sdkType) {
                return -1;
            } else if (sdk2.getSdkType() == sdkType) {
                return 1;
            } else {
                return 0;
            }
        };
        return SdkConfigurationUtil.findOrCreateSdk(sdkComparator, sdkType);
    }
}


