package net.masterthought.dlanguage.project;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportProvider;
import net.masterthought.dlanguage.module.DubBinaryForModuleStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class DubProjectImportProvider extends ProjectImportProvider {

    public DubProjectImportProvider(final DubProjectImportBuilder builder) {
        super(builder);
    }

    @Override
    public ModuleWizardStep[] createSteps(WizardContext wizardContext) {
        List<ModuleWizardStep> steps = new ArrayList<>();

        ModuleWizardStep setDubBinary = new DubBinaryForModuleStep(wizardContext);
        steps.add(setDubBinary);

        return steps.toArray(new ModuleWizardStep[steps.size()]);
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
        final VirtualFile[] dubFiles = fileOrDirectory.getChildren();

        final Optional<VirtualFile> optionalDubFile = Arrays.stream(dubFiles)
            .filter(f -> !f.isDirectory())
            .filter(f -> f.getNameWithoutExtension().equalsIgnoreCase("dub"))
            .findFirst();

        return optionalDubFile.isPresent() && canImportFromFile(optionalDubFile.get());
    }

    @Override
    public boolean canImportFromFile(VirtualFile file) {
        return true;
    }
}
