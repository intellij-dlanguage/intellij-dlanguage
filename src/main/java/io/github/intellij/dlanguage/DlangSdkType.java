package io.github.intellij.dlanguage;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.projectRoots.impl.DependentSdkType;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.library.LibFileRootType;
import io.github.intellij.dlanguage.sdk.SetupStatus;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Responsible for the mechanics when pressing "+" in the SDK configuration, as well as the project
 * SDK configuration.
 *
 * This class was initially the only SdkType for the plugin but is now just providing common
 * functionality for the x3 variations of D compilers.
 *
 * @see io.github.intellij.dlanguage.sdk.DlangDmdSdkType
 * @see io.github.intellij.dlanguage.sdk.DlangGdcSdkType
 * @see io.github.intellij.dlanguage.sdk.DlangLdcSdkType
 */
public abstract class DlangSdkType extends DependentSdkType {

    protected static final Logger LOG = Logger.getInstance(DlangSdkType.class);

    protected final String compilerBinaryFilename;

    @Deprecated // todo: may need to remove this. it'll return any of the three D compiler types
    public static DlangSdkType getInstance() {
        //return SdkType.findInstance(DlangSdkType.class);
        for (SdkType sdkType : EP_NAME.getExtensionList()) {
            if (DlangSdkType.class.isAssignableFrom(sdkType.getClass())) {
                return (DlangSdkType) sdkType;
            }
        }
        throw new IllegalArgumentException("Cannot find any implementation of DlangSdkType");
    }

    public DlangSdkType(final String id, final String compilerBinaryFilename) {
        super(id);
        this.compilerBinaryFilename = compilerBinaryFilename;
    }

    /**
     * This method is overridden by {@link io.github.intellij.dlanguage.sdk.DlangDmdSdkType}, {@link io.github.intellij.dlanguage.sdk.DlangLdcSdkType}, and {@link io.github.intellij.dlanguage.sdk.DlangGdcSdkType}.
     * For DMD this will return dmd.conf or sc.ini (Windows uses sc.ini)
     * For LDC this will return ldc2.conf
     * For GDC it returns null
     * @return the filename that's used for compiler configuration
     */
    abstract protected @Nullable String getCompilerConfigFilename();

    @Override
    public @NotNull FileChooserDescriptor getHomeChooserDescriptor() {
        final FileChooserDescriptor descriptor = super.getHomeChooserDescriptor();
        // override the text in the popup title when selecting location of D compiler
        descriptor.setTitle(DlangBundle.INSTANCE.message("dlang.dependant.sdk.configure.home.title", getPresentableName()));
        return descriptor;
    }

    @Override
    public @NotNull @NlsContexts.Label String getHomeFieldLabel() {
        return DlangBundle.INSTANCE.message("dlang.dependant.sdk.configure.type.home.path", getPresentableName());
    }

    @NotNull
    @Override
    public String suggestSdkName(@Nullable final String currentSdkName, final String sdkHome) {
        try {
            final String version = Objects.requireNonNull(getCompilerVersion(sdkHome)).get(3L, TimeUnit.SECONDS);

            return StringUtil.isNotEmpty(version) ? version : this.getName();
        } catch (InterruptedException | TimeoutException | java.util.concurrent.ExecutionException e) {
            LOG.error("unable to establish version", e);
        }
        return this.getName();
    }

    /**
     * When user set up DMD SDK path this method checks if specified path contains a D compiler executable.
     *
     * This method determines if it can run the dmd executable based on a home path that's passed in.
     * So in the case that the sdk home is "C:\D\dmd2\windows\bin" then this method would return true
     * if "C:\D\dmd2\windows\bin\dmd.exe" exists and is executable. On Linux it could be a variety of
     * locations depending on distribution and installation type. eg: "/usr/bin", "/usr/local/bin", or others.
     *
     * @param path to the directory containing the compiler binary
     * @return true if the sdk home contains a executable D compiler
     */
    @Override
    public boolean isValidSdkHome(@NotNull String path) {
        final File compilerBinary = Paths.get(path, compilerBinaryFilename).toFile();
        return compilerBinary.exists() && compilerBinary.canExecute();
    }

    @Nullable
    @Override
    public String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
        return "https://dlang.org/phobos/index.html"; // there is also "https://dlang.org/spec/spec.html"
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
//                                   @NotNull Consumer<? super Sdk> sdkCreatedCallback) {
//        LOG.info("attempt to display custom UI for creating D sdk");
//
//        SdkConfigurationUtil.selectSdkHome(this, home -> {
//            //final String newSdkName = SdkConfigurationUtil.createUniqueSdkName(this, home, Arrays.asList(sdkModel.getSdks()));
//            //final Sdk sdk = DlangSdkType.findOrCreateSdk();
//            //final Sdk sdk = new io.github.intellij.dlanguage.settings.DlangCompiler(newSdkName, home, "???");
//
//            sdkCreatedCallback.consume(sdk);
//        });
//    }

    protected Optional<VirtualFile> firstVirtualFileFrom(@NotNull final File[] files) {
        for (File f: files) {
            if (f.isDirectory()) {
                return Optional.ofNullable(
                    LocalFileSystem.getInstance().findFileByPath(f.getAbsolutePath())
                );
            }
        }
        return Optional.empty();
    }

    protected Optional<VirtualFile> firstVirtualFileFrom(@NotNull final String[] paths) {
        for (final String p: paths) {
            final File f = Paths.get(p).toFile();
            if (f.isDirectory()) {
                return Optional.ofNullable(
                    LocalFileSystem.getInstance().findFileByPath(f.getAbsolutePath())
                );
            }
        }
        return Optional.empty();
    }

    protected abstract void attachDruntimeSources(@NotNull final SdkModificator sdkModificator, @NotNull final SetupStatus status);

    protected abstract void attachPhobosSources(@NotNull final SdkModificator sdkModificator, @NotNull final SetupStatus status);

    // todo: move to DlangDmdSdkType.kt
    protected void setupDocumentationPath(@NotNull final Sdk sdk, final SdkModificator sdkModificator, final SetupStatus status) {
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

    @Nullable
    protected abstract String locateCompilerConfig(final Sdk sdk);

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(
                                            @NotNull final SdkModel sdkModel,
                                            @NotNull final SdkModificator sdkModificator) {
        return null; // it's ok to return null but would be good to see what we could do with this
    }

    @Override
    public void saveAdditionalData(@NotNull final SdkAdditionalData sdkAdditionalData, @NotNull final Element element) {
        //pass
    }

    @Override
    public boolean allowWslSdkForLocalProject() {
        return true;
    }

    @Override // called when creating the UI for 'Project Structure > SDKs'
    public boolean isRootTypeApplicable(@NotNull final OrderRootType type) {
        return OrderRootType.SOURCES == type || LibFileRootType.getInstance() == type; // for D compilers we only configure Phobos & Druntime sources (potentially may get documentation working as well)
    }

    /*
     * Due to differences in how the various D compilers are installed across platforms,
     * it's necessary to implement this method for each compiler type.
     * For example, on Windows:
     *  DMD home is "C:\D\dmd2\windows\bin", binary is "C:\D\dmd2\windows\bin\dmd.exe"
     *  LDC home is "C:\Program Files\LDC x.xx\bin", binary is "C:\Program Files\LDC x.xx\bin\ldc2.exe"
     *  LDC home is "C:\LDC\ldc2-x.xx.x-windows-multilib\bin", binary is "C:\LDC\ldc2-x.xx.x-windows-multilib\bin\ldc2.exe"
     * Whilst on Linux the binary and phobos/druntime will be in different locations and depending on distro or
     * install method the location of the binary could be "/usr/bin", "/usr/local/bin", or elsewhere.
     *  DMD on Fedora (via RPM installation), as well as LDC and GDC, are in "/usr/bin"
     *
     * @param sdkHome the home directory path for a D compiler
     * @return the compiler binary
     */
    protected final File getBinaryFile(@NotNull final String sdkHome) {
        return Paths.get(sdkHome, this.compilerBinaryFilename).toFile();
    }

    /**
     * Try to execute 'dmd --version' and return first line of the output.
     *
     * @param sdkHome path to dmd home directory
     * @return String containing DMD version or null
     */
    @NotNull
    protected Future<String> getCompilerVersion(final String sdkHome) {
        return ApplicationManager.getApplication().executeOnPooledThread(() -> {
            if (isValidSdkHome(sdkHome)) {
                final GeneralCommandLine cmd = new GeneralCommandLine();
                //cmd.withWorkDirectory(sdkHome.getAbsolutePath());
                cmd.setExePath(getBinaryFile(sdkHome).getAbsolutePath());
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
     * Returns full path to D compiler executable for the given Sdk (based on the home path).
     * If the Sdk doesn't have a valid value then this method will simply return the binary name in the hope that it's on the PATH
     * @param sdk an Sdk of {@link DlangSdkType}
     * @return Either the absolute path to the dmd compiler or simply the binary name, eg: "dmd", "ldc2", "gdc"
     */
    public String getDlangCompilerPath(@NotNull final Sdk sdk) {
        @Nullable final String homePath = sdk.getHomePath();

        if (homePath != null && isValidSdkHome(homePath)) {
            return Paths.get(homePath, compilerBinaryFilename).toFile().getAbsolutePath();
        }

        LOG.warn(String.format("Home path '%s' for dlang sdk was not valid. Falling back to '%s'", homePath, compilerBinaryFilename));

        return compilerBinaryFilename; // it may just be on the PATH
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


