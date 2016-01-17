package net.masterthought.dlanguage.project;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportProvider;
import net.masterthought.dlanguage.DLanguageSdkType;
import net.masterthought.dlanguage.module.DubInitForModuleStep;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DubProjectImportProvider extends ProjectImportProvider {

    public DubProjectImportProvider(final DubProjectImportBuilder builder) {
        super(builder);
    }

    @Override
    public ModuleWizardStep[] createSteps(WizardContext wizardContext) {
        return ModuleWizardStep.EMPTY_ARRAY;
//        List<ModuleWizardStep> steps = new ArrayList<>();
//
////        ModuleWizardStep setCompiler = new ProjectJdkForModuleStep(wizardContext, DLanguageSdkType.getInstance()) {
////            public void updateDataModel() {
////                super.updateDataModel();
////                moduleBuilder.setModuleJdk(getJdk());
////            }
////        };
//        ModuleWizardStep setDubInit = new DubInitForModuleStep(wizardContext);
//
////        steps.add(setCompiler);
//
////        if((moduleBuilder.getBuilderId() != null && moduleBuilder.getBuilderId().equals("DLangDubApp"))){
//            steps.add(setDubInit);
////        }
//
//        return steps.toArray(new ModuleWizardStep[steps.size()]);
    }

    @Override
    public boolean canImport(VirtualFile fileOrDirectory, @Nullable Project project) {
        // If we're not importing a directory, validate it as a file.
        if (!fileOrDirectory.isDirectory()) return canImportFromFile(fileOrDirectory);

        VirtualFile dubFile = fileOrDirectory.findChild("dub.json");
        if(dubFile != null){
            if(canImportFromFile(dubFile)) return true;
        }
        VirtualFile[] dubFiles = fileOrDirectory.getChildren();
        List<Boolean> vfiles = new ArrayList<>();
        for(VirtualFile vfile : dubFiles){
            if(!vfile.isDirectory() && vfile.getName().equals("dub.json")) {
                vfiles.add(canImportFromFile(vfile));
            }
        }
        return !vfiles.contains(false);
    }

    @Override
    public boolean canImportFromFile(VirtualFile file) {
        return true;
    }

//    override def canImportFromFile(file: VirtualFile): Boolean = {
//        try {
//        val content = new String(file.contentsToByteArray(), CharsetToolkit.UTF8)
//        // Bail out if we can't parse the yaml file.
//        if (StackYaml.fromString(content).isLeft) return false
//        // If we can parse it, go ahead and update the builder accordingly.
//        builder.params.stackYamlPath = Some(file.getCanonicalPath)
//        true
//        } catch {
//        // If we can't read the file, we can't import from it.
//        case e: java.io.IOException =>
//        false
//        }
//        }
}



///**
// * Provides the UI for importing a Stack project.
// */
//class StackProjectImportProvider(builder: StackProjectImportBuilder)
//        extends ProjectImportProvider(builder) {
//        override def createSteps(context: WizardContext): Array[ModuleWizardStep] = Array(
//        new StackProjectImportStep(context),
//        new StackSelectImportedProjectsStep(context)
//        )
//
//        /**
//         * Checks to see if a given file or directory can be imported as a Stack project.
//         */
//        override def canImport(fileOrDirectory: VirtualFile, project: Project): Boolean = {
//        // If we're not importing a directory, validate it as a file.
//        if (!fileOrDirectory.isDirectory) return canImportFromFile(fileOrDirectory)
//        for (stackFile <- Option(fileOrDirectory.findChild("stack.yaml"))) {
//        if (canImportFromFile(stackFile)) return true
//        }
//        val stackFiles = fileOrDirectory.getChildren.filter(f =>
//        !f.isDirectory && f.getName != "stack.yaml"
//        )
//        stackFiles.exists(canImportFromFile)
//        }
//
//        /**
//         * Confirms that the Stack config file to be imported is valid
//         * and, if so, set the builder's parameters accordingly.
//         */
//        override def canImportFromFile(file: VirtualFile): Boolean = {
//        try {
//        val content = new String(file.contentsToByteArray(), CharsetToolkit.UTF8)
//        // Bail out if we can't parse the yaml file.
//        if (StackYaml.fromString(content).isLeft) return false
//        // If we can parse it, go ahead and update the builder accordingly.
//        builder.params.stackYamlPath = Some(file.getCanonicalPath)
//        true
//        } catch {
//        // If we can't read the file, we can't import from it.
//        case e: java.io.IOException =>
//        false
//        }
//        }
//        }
