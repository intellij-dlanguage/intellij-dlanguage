package io.github.intellij.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageModuleDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.psi.util.PsiTreeUtil.findChildOfType;

/**
 * Performs creation of element types.
 */
public class DElementFactory {

    /**
     * Takes a name and returns a Psi node of that name, or null.
     */
    @Nullable
    public static DlangIdentifier createDLanguageIdentifierFromText(@NotNull final Project project, @NotNull final String name) {
        final DlangIdentifier e = findChildOfType(createExpressionFromText(project, name), DlangIdentifier.class);
        if (e != null && e.getName().equals(name)) return e;
        return null;
    }

    /**
     * Takes an expression in text and returns a Psi tree of that program.
     */
    @NotNull
    private static PsiElement createExpressionFromText(@NotNull final Project project, @NotNull final String name) {
        final DlangFile fileFromText = createFileFromText(project, name);
        final PsiElement rhs = fileFromText.getFirstChild().getFirstChild().getLastChild();
        return rhs.getLastChild().getLastChild().getLastChild();
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    private static DlangFile createFileFromText(@NotNull final Project project, @NotNull final String text) {
        return (DlangFile) PsiFileFactory.getInstance(project).createFileFromText("A.hs", DLanguage.INSTANCE, text);
    }

    public static PsiElement createDLanguageModuleFromText(final Project project, final String name) {
        final PsiElement e = createExpressionFromText(project, "module " + name + ";").getFirstChild();
        if (e instanceof DLanguageModuleDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageSingleImportFromText(final Project project, final String name) {
        final PsiElement e = createExpressionFromText(project, "import " + name + ";").getFirstChild();
        if (e instanceof DLanguageImportDeclaration) return e;
        return null;
    }

    public static DLanguageAliasDeclaration createAliasDeclarationFromText(final Project project,
        final String text) {
        final PsiFile fileFromText = PsiFileFactory.getInstance(project)
            .createFileFromText("A.d", DLanguage.INSTANCE, text);
        return PsiTreeUtil.findChildOfType(fileFromText, DLanguageAliasDeclaration.class);
    }
}

