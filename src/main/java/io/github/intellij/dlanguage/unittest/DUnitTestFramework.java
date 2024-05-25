package io.github.intellij.dlanguage.unittest;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAtAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Provides the support for d-unit
 */
public class DUnitTestFramework extends DlangTestFramework {

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

        if (element.getLanguage() == DLanguage.INSTANCE) {
            final Collection<DLanguageAtAttribute> udas = PsiTreeUtil.findChildrenOfType(element, DLanguageAtAttribute.class);
            return !udas.isEmpty();
        } else {
            return false;
        }
    }

}
