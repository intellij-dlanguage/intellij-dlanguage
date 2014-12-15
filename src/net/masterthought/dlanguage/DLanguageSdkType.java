package net.masterthought.dlanguage;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import net.masterthought.dlanguage.jps.model.JpsDLanguageModelSerializerExtension;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * Responsible for the mechanics when pressing "+" in the SDK configuration,
 * as well as the project SDK configuration.
 */
public class DLanguageSdkType extends SdkType {
    // Messages go to the log available in Help -> Show log in finder.
    private final static Logger LOG = Logger.getInstance(DLanguageSdkType.class);

    public DLanguageSdkType() {
        super(JpsDLanguageModelSerializerExtension.DLANGUAGE_SDK_TYPE_ID);
    }

    /**
     * Returns the D Language SDK.
     */
    @NotNull
    public static DLanguageSdkType getInstance() {
        return SdkType.findInstance(DLanguageSdkType.class);
    }

    /**
     * Returns the icon to be used for Haskell things in general.
     */
    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(SdkModel sdkModel,
                                                                       SdkModificator sdkModificator) {
        return null;
    }

    @Override
    public String getPresentableName() {
        return JpsDLanguageModelSerializerExtension.DLANGUAGE_SDK_TYPE_ID;
    }

    /**
     * Currently a no-op.
     */
    @Override
    public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {
    }

    /**
     * Approves of a path as SDK home.
     */
    @Override
    public boolean isValidSdkHome(String path) {
        File dmd = getCompilerExecutable(path);
        return dmd.canExecute();
    }

    @NotNull
    public static File getCompilerExecutable(@NotNull String sdkHome) {
        return new File(FileUtil.join(new File(sdkHome, "bin").getAbsolutePath(), "dmd"));
    }

    /**
     * Always suggests "DMD" as the SDK name. We do not support anything else
     * in practice anyways.
     */
    @Override
    public String suggestSdkName(String currentSdkName, String sdkHome) {
        return "DMD";
    }

    /**
     * Returns the d version.
     */
    @Nullable
    @Override
    public String getVersionString(@NotNull String sdkHome) {
        return DSdkUtil.getSdkVersion(sdkHome);
    }

    /**
     * Suggests a home path.
     */
    @Nullable
    @Override
    public String suggestHomePath() {
        if (SystemInfo.isWindows) {
            // TODO: Windows SDK support
            return null;
        } else if (SystemInfo.isMac) {
            File homebrewRoot = new File("/usr/local/opt/dmd");
            if (homebrewRoot.exists()) {
                return homebrewRoot.getPath();
            }
            return null;
        } else if (SystemInfo.isLinux) {
            // TODO: Linux SDK support
            return null;
        }
        return null;
    }

    /**
     * Produces the grey "7.6.3" version number next to GHC in the project
     * settings.
     */
    @Override
    public boolean setupSdkPaths(Sdk sdk, SdkModel sdkModel) {
        super.setupSdkPaths(sdk, sdkModel);
        final SdkModificator sdkModificator = sdk.getSdkModificator();
        final String homePath = sdk.getHomePath();
        sdkModificator.setVersionString(getVersionString(homePath));
        sdkModificator.commitChanges();
        return true;
    }

    /**
     * Best effort at locating DMD according to given path.
     */
    @NotNull
    public static File getExecutable(@NotNull final String path) {
        // We might get called with /usr/local/bin, or the true SDK
        // path. The goal is to run ghc at this stage, so adapt to whatever.
        String extra = path.endsWith("bin") ? "" : File.separator + "bin";
        return new File(path + extra, SystemInfo.isWindows ? "dmd.exe" : "dmd");
    }

    @Nullable
    public static File getExecutable(@NotNull final Project project) {
        final String sdkPath = getDSdkPath(project);
        return sdkPath == null ? null : getExecutable(sdkPath);
    }

    /**
     * Gets the D Language SDK path for a project.
     */
    public static String getDSdkPath(@NotNull Project project) {
        Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
        // This sdk is not an instanceof DLanguageSdkType. Compare with name instead.
        if (sdk == null || !sdk.getSdkType().toString().equals(JpsDLanguageModelSerializerExtension.DLANGUAGE_SDK_TYPE_ID)) {
            return null;
        }
        return sdk.getHomePath();
    }
}

