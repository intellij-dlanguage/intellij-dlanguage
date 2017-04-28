package net.masterthought.dlanguage.project;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportProvider;
import net.masterthought.dlanguage.module.DubBinaryForModuleStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


public class DubProjectImportProvider extends ProjectImportProvider {

    public DubProjectImportProvider(final DubProjectImportBuilder builder) {
        super(builder);
    }

    @Override
    public ModuleWizardStep[] createSteps(WizardContext wizardContext) {
        final ModuleWizardStep setDubBinary = new DubBinaryForModuleStep(wizardContext);

        return new ModuleWizardStep[] { setDubBinary };
    }

    @Override
    public boolean canImport(@NotNull final VirtualFile fileOrDirectory, @Nullable Project project) {
        // If we're not importing a directory, validate it as a file.
        if (!fileOrDirectory.isDirectory()) return canImportFromFile(fileOrDirectory);

        // check for dub.json
        final VirtualFile dubJson = fileOrDirectory.findChild("dub.json");
        if(dubJson != null) {
            if(canImportFromFile(dubJson)) {
                return true;
            }
        }

        // check for dub.sdl
        final VirtualFile dubSdl = fileOrDirectory.findChild("dub.sdl");
        if(dubSdl != null) {
            if(canImportFromFile(dubSdl)) {
                return true;
            }
        }

        // alternatively, check all the children for a dub.json or a dub.sdl
        return Arrays.stream(fileOrDirectory.getChildren())
            .filter(f -> !f.isDirectory())
            .anyMatch(this::canImportFromFile);
    }

    @Override
    public boolean canImportFromFile(final VirtualFile file) {
        return "dub.json".equalsIgnoreCase(file.getName()) || "dub.sdl".equalsIgnoreCase(file.getName());
    }
}
