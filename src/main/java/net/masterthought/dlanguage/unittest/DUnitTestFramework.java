package net.masterthought.dlanguage.unittest;

import com.intellij.icons.AllIcons;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.lang.Language;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testIntegration.TestFramework;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DLanguageAtAttribute;
import net.masterthought.dlanguage.psi.DLanguageFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class DUnitTestFramework implements TestFramework {
    @NotNull
    @Override
    public String getName() {
        return DLanguage.INSTANCE.getDisplayName();
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return AllIcons.RunConfigurations.Junit;
    }

    @Override
    public boolean isLibraryAttached(@NotNull final Module module) {
        return false;
    }

    @Nullable
    @Override
    public String getLibraryPath() {
        return null;
    }

    @Nullable
    @Override
    public String getDefaultSuperClass() {
        return null;
    }

    @Override
    public boolean isTestClass(@NotNull final PsiElement clazz) {
//        return new ApexUnitTestFinder().isTest(clazz);
        return true;
    }

    @Override
    public boolean isPotentialTestClass(@NotNull final PsiElement clazz) {
        return isTestClass(clazz);
    }

    @Nullable
    @Override
    public PsiElement findSetUpMethod(@NotNull final PsiElement clazz) {
        // TODO: However you find a setup method, if applicable. For example, in mine they’re annotated with @TestSetup.
        return null;
    }

    @Nullable
    @Override
    public PsiElement findTearDownMethod(@NotNull final PsiElement clazz) {
        // TODO: However you find a teardown method, if applicable.  Apex doesn’t have that notion.
        return null;
    }

    @Nullable
    @Override
    public PsiElement findOrCreateSetUpMethod(@NotNull final PsiElement clazz) throws IncorrectOperationException {
        final PsiElement setUpMethod = findSetUpMethod(clazz);
        if (setUpMethod != null) {
            return setUpMethod;
        } else {
            // TODO: Add support for this
            throw new IncorrectOperationException("No @TestSetup method and creation is not yet supported.");
        }
    }

    @Override
    public FileTemplateDescriptor getSetUpMethodFileTemplateDescriptor() {
        // TODO: Add support for this
        return null;
    }

    @Override
    public FileTemplateDescriptor getTearDownMethodFileTemplateDescriptor() {
        return null;
    }

    @Override
    public FileTemplateDescriptor getTestMethodFileTemplateDescriptor() {
        // TODO: Add support for this
        return null;
    }

    @Override
    public boolean isIgnoredMethod(final PsiElement element) {
        return false;
    }

    @Override
    public boolean isTestMethod(final PsiElement element) {
        // TODO: However you determine whether a method is a test method.  For example, in mine they’re annotated with @Test or the testMethod modifier

        if (element.getClass() == DLanguageFile.class) {
            final Collection<DLanguageAtAttribute> udas = PsiTreeUtil.findChildrenOfType(element, DLanguageAtAttribute.class);
            return !udas.isEmpty();
        } else {
            return false;
        }
    }

    @NotNull
    @Override
    public Language getLanguage() {
        return DLanguage.INSTANCE;
    }


}
