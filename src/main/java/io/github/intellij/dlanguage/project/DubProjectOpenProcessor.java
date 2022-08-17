package io.github.intellij.dlanguage.project;

import com.intellij.ide.GeneralSettings;
import com.intellij.ide.impl.OpenProjectTask;
import com.intellij.ide.impl.ProjectUtil;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectOpenProcessor;
import com.intellij.util.ArrayUtil;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.module.DlangModuleBuilder;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;

/**
 * Used when opening a dub project within the IDE.
 *
 * Some time ago we ended having two ProjectOpenProcessors: this one and CLionDubProjectOpenProcessor.
 * Reason being is that this class was using code from java-impl that would not work in CLion:
 *      com.intellij.projectImport.ProjectImportBuilder
 *      com.intellij.projectImport.ProjectOpenProcessorBase
 * This should be revisited so that there is a single ProjectOpenProcessor (this one), which works in all
 * Intellij IDEs. Alternatively this could be a base class with both an IDEA specific version and a CLion one.
 */
public class DubProjectOpenProcessor extends ProjectOpenProcessor {

    private static final Logger LOG = Logger.getInstance(DubProjectOpenProcessor.class.getName());

    private static final String NAME = "Dub";
    private static final String[] SUPPORTED_FILES = {"dub.json", "dub.sdl"};

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DLanguage.Icons.FILE;
    }

    @Override
    public boolean canOpenProject(@NotNull final VirtualFile file) {
        if (file.isDirectory()) {
            final VirtualFile[] children = file.getChildren();
            if (children != null) {
                return Arrays.stream(children)
                    .anyMatch(child -> ArrayUtil.contains(child.getName(), SUPPORTED_FILES));
            }
        } else {
            return ArrayUtil.contains(file.getName(), SUPPORTED_FILES);
        }
        return false;
    }

    @Override
    public @Nullable Project doOpenProject(@NotNull VirtualFile virtualFile, @Nullable Project projectToClose, boolean forceOpenInNewFrame) {
        if (projectToClose != null && !forceOpenInNewFrame) {
            final int exitCode = ProjectUtil.confirmOpenNewProject(false);
            if (exitCode == GeneralSettings.OPEN_PROJECT_SAME_WINDOW) {
                if (!ProjectManagerEx.getInstanceEx().closeAndDispose(projectToClose)) {
                    return null;
                }
            } else if (exitCode != GeneralSettings.OPEN_PROJECT_NEW_WINDOW) {
                // not in a new window
                return null;
            }
        }

        final VirtualFile baseDir = virtualFile.isDirectory() ? virtualFile : virtualFile.getParent();

        final Project project = ProjectManagerEx.getInstanceEx()
            .newProject(baseDir.toNioPath(), OpenProjectTask.build().withProjectName(baseDir.getName()));

        if (project != null) {
            WriteAction.run(() -> {
                final Sdk sdk = DlangSdkType.findOrCreateSdk();
                ProjectRootManager.getInstance(project).setProjectSdk(sdk);
                final DlangModuleBuilder builder = new DlangModuleBuilder();
                builder.setModuleJdk(sdk);
                builder.commit(project);
            });
            ProjectManagerEx.getInstanceEx().openProject(project);
        }
        return project;
    }

//    @NotNull
//    @Override
//    protected DubProjectImportBuilder doGetBuilder() {
//        return ProjectImportBuilder.EXTENSIONS_POINT_NAME.findExtensionOrFail(DubProjectImportBuilder.class);
//    }

    //@Override
    public boolean doQuickImport(@NotNull final VirtualFile file, @NotNull final WizardContext context) {
        final DubProjectImportBuilder builder = DubProjectImportBuilder.EXTENSIONS_POINT_NAME.findExtensionOrFail(DubProjectImportBuilder.class);
        final VirtualFile rootDirectory = file.getParent();

        if(StringUtil.isNotEmpty(ToolKey.DUB_KEY.getPath())) {
            builder.getParameters().dubBinary = ToolKey.DUB_KEY.getPath();
            //builder.setRootDirectory(context.getProjectFileDirectory());
            builder.setRootDirectory(rootDirectory.getPath());
            context.setProjectName(rootDirectory.getName());
            LOG.debug("Opening dub project");
            return true;
        } else {
            LOG.warn("Couldn't open project as dub not configured");
            Messages.showInfoMessage(DlangBundle.INSTANCE.message("d.ui.projectopen.missing.dub.binary"), DlangBundle.INSTANCE.message("d.ui.projectopen.dub"));
            return false;
        }
    }
}
