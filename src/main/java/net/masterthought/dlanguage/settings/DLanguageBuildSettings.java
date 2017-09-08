package net.masterthought.dlanguage.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.jps.model.DLanguageBuildOptions;
import net.masterthought.dlanguage.jps.model.JpsDLanguageBuildOptionsSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = JpsDLanguageBuildOptionsSerializer.DLANGUAGE_BUILD_OPTIONS_COMPONENT_NAME,
    storages = {
        @Storage(file = StoragePathMacros.MODULE_FILE),
        @Storage(value = "/compiler.xml", scheme = StorageScheme.DIRECTORY_BASED)
    }
)
public class DLanguageBuildSettings implements PersistentStateComponent<DLanguageBuildOptions> {

    private static final Logger LOG = Logger.getInstance(DLanguageBuildSettings.class);

    private DLanguageBuildOptions myBuildOptions = new DLanguageBuildOptions();

    @NotNull
    public static DLanguageBuildSettings getInstance(@NotNull final Project project) {
        final DLanguageBuildSettings persisted = ServiceManager.getService(project, DLanguageBuildSettings.class);
        return persisted != null ? persisted : new DLanguageBuildSettings();
    }

    @Nullable
    @Override
    public DLanguageBuildOptions getState() {
        return myBuildOptions;
    }

    @Override
    public void loadState(final DLanguageBuildOptions state) {
        LOG.info("loading build options : " + state);
        myBuildOptions = state;
    }

    @NotNull
    public String getDmdPath() {
        return myBuildOptions.myDmdPath;
    }

    public void setDmdPath(@NotNull final String path) {
        myBuildOptions.myDmdPath = path;
    }

    @NotNull
    public String getrDmdPath() {
        return myBuildOptions.myrDmdPath;
    }

    public void setrDmdPath(@NotNull final String path) {
        myBuildOptions.myrDmdPath = path;
    }
}
