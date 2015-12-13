package net.masterthought.dlanguage.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.xmlb.annotations.Property;
import org.jetbrains.annotations.NotNull;

@State(
    name = "DLanguage.Settings",
    storages = {@Storage(file = StoragePathMacros.WORKSPACE_FILE)}
)
public class DLanguageGeneralSettings implements PersistentStateComponent<DLanguageGeneralSettings.State> {
    @NotNull
    public static DLanguageGeneralSettings getInstance(@NotNull Project project) {
        DLanguageGeneralSettings persisted = ServiceManager.getService(project, DLanguageGeneralSettings.class);
        return persisted != null ? persisted : new DLanguageGeneralSettings();
    }

    public static class State {
        public String dubExecutablePath;

        public State() {
            dubExecutablePath = SystemInfo.isWindows ? "dub.exe" : "dub";
        }

        @Override
        public boolean equals(Object that) {
            if(that instanceof State) {
                State thatState = (State) that;
                return this.dubExecutablePath.equals(thatState.dubExecutablePath);
            }
            return false;
        }
    }

    private State myState = new State();

    @Property
    @NotNull
    public String getDubExecutablePath() {
        return myState.dubExecutablePath;
    }

    public void setDubExecutablePath(@NotNull final String dmdExecutablePath) {
        if (!this.myState.dubExecutablePath.equals(dmdExecutablePath)) {
            this.myState.dubExecutablePath = dmdExecutablePath;
        }
    }

    public State getState() {
        return myState;
    }

    public void loadState(State state) {
        myState = state;
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof DLanguageGeneralSettings) {
            return this.myState.equals(((DLanguageGeneralSettings)that).myState);
        }
        else {
            return false;
        }
    }
}
