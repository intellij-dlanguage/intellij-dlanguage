//package io.github.intellij.dlanguage.project;
//
//import com.intellij.ide.util.projectWizard.WizardContext;
//import com.intellij.openapi.options.ConfigurationException;
//import com.intellij.openapi.vfs.VirtualFile;
//import com.intellij.projectImport.ProjectOpenProcessorBase;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//
//public class DubProjectOpenProcessor extends ProjectOpenProcessorBase<DubProjectImportBuilder> {
//    public DubProjectOpenProcessor(@NotNull final DubProjectImportBuilder builder) {
//        super(builder);
//    }
//
//    @Nullable
//    public String[] getSupportedExtensions() {
//        return new String[] {"dub.json"};
//    }
//
//    public boolean doQuickImport(VirtualFile file, final WizardContext wizardContext) {
//        getBuilder().setRootDirectory(file.getParent().getPath());
//
//        final List<String> projects = getBuilder().getList();
//        if (projects == null || projects.size() != 1) {
//            return false;
//        }
//        try {
//            getBuilder().setList(projects);
//        } catch (ConfigurationException e) {
//            e.printStackTrace();
//        }
//        wizardContext.setProjectName("woops");
//        return true;
//    }
//}
