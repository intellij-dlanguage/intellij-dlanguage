package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageImportDeclaration;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
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
    public static DLanguageIdentifier createDLanguageIdentifierFromText(@NotNull final Project project, @NotNull final String name) {
        final DLanguageIdentifier e = findChildOfType(createExpressionFromText(project, name), DLanguageIdentifier.class);
        if (e != null && e.getName().equals(name)) return e;
        return null;
    }

    /**
     * Takes an expression in text and returns a Psi tree of that program.
     */
    @NotNull
    private static PsiElement createExpressionFromText(@NotNull final Project project, @NotNull final String name) {
        final DLanguageFile fileFromText = createFileFromText(project, name);
        final PsiElement rhs = fileFromText.getFirstChild().getFirstChild().getLastChild();
        return rhs.getLastChild().getLastChild().getLastChild();
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    private static DLanguageFile createFileFromText(@NotNull final Project project, @NotNull final String text) {
        return (DLanguageFile) PsiFileFactory.getInstance(project).createFileFromText("A.hs", DLanguage.INSTANCE, text);
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
}

