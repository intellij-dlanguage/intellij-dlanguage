package io.github.intellij.dlanguage.project;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportBuilder;
import com.intellij.projectImport.ProjectOpenProcessorBase;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import io.github.intellij.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Used when opening a dub project within the IDE
 */
public class DubProjectOpenProcessor extends ProjectOpenProcessorBase<DubProjectImportBuilder> {

    private static final Logger LOG = Logger.getInstance(DubProjectOpenProcessor.class.getName());

    @NotNull
    @Override
    public String[] getSupportedExtensions() {
        return CLionDubProjectOpenProcessor.SUPPORTED_FILES;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return DlangIcons.FILE;
    }

    @NotNull
    @Override
    protected DubProjectImportBuilder doGetBuilder() {
        return ProjectImportBuilder.EXTENSIONS_POINT_NAME.findExtensionOrFail(DubProjectImportBuilder.class);
    }

    @Override
    public boolean doQuickImport(@NotNull final VirtualFile file, @NotNull final WizardContext context) {
        final DubProjectImportBuilder builder = getBuilder();
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
