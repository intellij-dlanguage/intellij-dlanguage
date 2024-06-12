package io.github.intellij.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageSpecifiedVariableDeclaration;
import io.github.intellij.dlanguage.psi.DlangPsiFile;
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration;
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
    public static PsiElement createDLanguageIdentifierFromText(@NotNull final Project project, @NotNull final String name) {
        if (name.isBlank()) return null;
        @Nullable PsiElement element = createExpressionFromText(project, "int " + name + ";");
        assert element != null;
        final PsiElement e = ((DLanguageSpecifiedVariableDeclaration) element).getIdentifierInitializers().get(0).getIdentifier();

        return e != null && e.getText().equals(name) ? e : null;
    }

    /**
     * Takes an expression in text and returns a Psi tree of that program.
     */
    @Nullable
    private static PsiElement createExpressionFromText(@NotNull final Project project, @NotNull final String name) {
        @NotNull final DlangPsiFile fileFromText = createFileFromText(project, name);

        @Nullable final PsiElement firstChild = fileFromText.getFirstChild();

        return firstChild;
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    private static DlangPsiFile createFileFromText(@NotNull final Project project,
                                                @NotNull final String text) {
        return (DlangPsiFile) PsiFileFactory.getInstance(project)
            .createFileFromText("A.d", DLanguage.INSTANCE, text);
    }

    @Nullable
    public static DlangModuleDeclaration createDLanguageModuleFromText(@NotNull final Project project,
                                                                       @NotNull final String name) {
        if(StringUtil.isEmptyOrSpaces(name)) {
            return null; // perhaps should throw exception
        }
        return findChildOfType(
            createFileFromText(project, "module " + name + ";"),
            DlangModuleDeclaration.class
        );
    }

    @Nullable
    public static DLanguageAliasDeclaration createAliasDeclarationFromText(@NotNull final Project project,
                                                                           @NotNull final String text) {
        final PsiFile fileFromText = PsiFileFactory.getInstance(project)
                                                   .createFileFromText("A.d", DLanguage.INSTANCE, text);
        return findChildOfType(fileFromText, DLanguageAliasDeclaration.class);
    }
}

