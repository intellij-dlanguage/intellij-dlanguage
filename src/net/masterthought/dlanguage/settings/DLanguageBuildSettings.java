package net.masterthought.dlanguage.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.jps.model.DLanguageBuildOptions;
import net.masterthought.dlanguage.jps.model.JpsDLanguageBuildOptionsSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = JpsDLanguageBuildOptionsSerializer.DLANGUAGE_BUILD_OPTIONS_COMPONENT_NAME,
        storages = {
                @Storage(file = StoragePathMacros.PROJECT_FILE),
                @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/compiler.xml",
                        scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class DLanguageBuildSettings implements PersistentStateComponent<DLanguageBuildOptions> {
    private DLanguageBuildOptions myBuildOptions = new DLanguageBuildOptions();

    @Nullable
    @Override
    public DLanguageBuildOptions getState() {
        return myBuildOptions;
    }

    @Override
    public void loadState(DLanguageBuildOptions state) {
        myBuildOptions = state;
    }

    @NotNull
    public String getDmdPath() {
        return myBuildOptions.myDmdPath;
    }

    public void setDmdPath(@NotNull String path) {
        myBuildOptions.myDmdPath = path;
    }


    @NotNull
    public static DLanguageBuildSettings getInstance(@NotNull Project project) {
        final DLanguageBuildSettings persisted = ServiceManager.getService(project, DLanguageBuildSettings.class);
        return persisted != null ? persisted : new DLanguageBuildSettings();
    }
}