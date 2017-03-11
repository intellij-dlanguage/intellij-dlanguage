package net.masterthought.dlanguage.actions;

import com.intellij.ide.actions.CreateFileAction;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.utils.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class CreateDLanguageFileAction extends CreateFileFromTemplateAction implements DumbAware {
    private static final String NEW_D_FILE = "New D File";

    private static final String EMPTY_MODULE_TEMPLATE = "D Language Module";

    public CreateDLanguageFileAction() {
        super(NEW_D_FILE, "", DLanguageIcons.FILE);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle(NEW_D_FILE)
                .addKind("Empty module", DLanguageIcons.FILE, EMPTY_MODULE_TEMPLATE)
                .setValidator(new ModuleNameValidator());
    }

    @Override
    protected PsiFile createFileFromTemplate(@NotNull String name, @NotNull FileTemplate template, @NotNull PsiDirectory dir) {
        final String validName = name.endsWith(".d")? name.substring(0, name.lastIndexOf('.')) : name;

        final List<String> pathParts = StringUtil.split(validName, ".");
        // Create any intermediate subdirectories.
        PsiDirectory subDir = dir;
        for (int i = 0; i < pathParts.size() - 1; ++i) {
            subDir = subDir.createSubdirectory(pathParts.get(i));
        }
        String moduleName = pathParts.get(pathParts.size() - 1);
        return createFileFromTemplate(moduleName, template, subDir, getDefaultTemplateProperty());
    }

    @SuppressWarnings("DialogTitleCapitalization")
    @Nullable
    private PsiFile createFileFromTemplate(@SuppressWarnings("NullableProblems") @NotNull String name,
                                             @NotNull FileTemplate template,
                                             @NotNull PsiDirectory dir,
                                             @Nullable String defaultTemplateProperty) {
        // TODO: Do we *have* to hack the IntelliJ source?
        // This is a roughly a copy/paste then slight adaptation from the IntelliJ definition of this method.
        // We can't override it directly, and more importantly we can't override its call to
        // FileTemplateUtil.createFromTemplate()
        List<String> pathItems = FileUtil.getPathFromSourceRoot(dir.getProject(), dir.getVirtualFile());
        // modulePrefix is the empty string if the module is either in the top
        // level directory or one of the subdirectories start with a lower-case
        // letter.
        final String modulePrefix = pathItems == null || pathItems.parallelStream().anyMatch(String::isEmpty) ? "" : StringUtil.join(pathItems, ".");


        // Adapted from super definition.
        CreateFileAction.MkDirs mkdirs = new CreateFileAction.MkDirs(name, dir);
        name = mkdirs.newName;
        dir = mkdirs.directory;

        final Project project = dir.getProject();

        try {
            // Patch props with custom property.
            final Properties props = FileTemplateManager.getInstance(project).getDefaultProperties();
            props.setProperty("DLANGUAGE_MODULE_NAME", modulePrefix.isEmpty() ? name : String.format("%s.%s", modulePrefix, name));
            final PsiElement element = FileTemplateUtil.createFromTemplate(template, name, props, dir);

            final PsiFile psiFile = element.getContainingFile();

            final VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile != null) {
                FileEditorManager.getInstance(project).openFile(virtualFile, true);
                if (defaultTemplateProperty != null) {
                    PropertiesComponent.getInstance(project).setValue(defaultTemplateProperty, template.getName());
                }
                return psiFile;
            }
        }
        catch (ParseException e) {
            Messages.showErrorDialog(project, String.format("Error parsing Velocity template: %s", e.getMessage()), "Create File from Template");
        }
        catch (IncorrectOperationException e) {
            LOG.error(e);
            throw e;
        }
        catch (Exception e) {
            LOG.error(e);
        }

        return null;
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return NEW_D_FILE;
    }

    private class ModuleNameValidator implements InputValidatorEx {

        private final Pattern VALID_MODULE_NAME_REGEX = Pattern.compile("^([A-Za-z0-9_]*)(\\.[A-Za-z0-9_]*)*(.d)?$");

        @Nullable
        @Override
        public String getErrorText(final String inputString) {
            if (inputString.isEmpty()) {
                return null;
            }
            if (VALID_MODULE_NAME_REGEX.matcher(inputString).matches()) {
                return null;
            }
            return String.format("\'%s\' is not a valid D module name.", inputString);
        }

        @Override
        public boolean checkInput(String inputString) {
            return true;
        }

        @Override
        public boolean canClose(String inputString) {
            return getErrorText(inputString) == null;
        }
    }
}

