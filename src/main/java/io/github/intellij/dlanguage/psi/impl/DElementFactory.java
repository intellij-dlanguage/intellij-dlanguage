package io.github.intellij.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
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
        @Nullable PsiElement element = createExpressionFromText(project, name);

        final DlangIdentifier e;
        if (element instanceof DlangIdentifier) {
            e = (DlangIdentifier) element;
        } else {
            e = findChildOfType(element, DlangIdentifier.class);
        }

        return e != null && e.getName().equals(name) ? e : null;
    }

    /**
     * Takes an expression in text and returns a Psi tree of that program.
     */
    @Nullable
    private static PsiElement createExpressionFromText(@NotNull final Project project, @NotNull final String name) {
        @NotNull final DlangFile fileFromText = createFileFromText(project, name);

        @Nullable final PsiElement firstChild = fileFromText.getFirstChild();

        // todo: this whole chain could do with being more defensive
        return firstChild != null ? firstChild
            .getFirstChild()
            .getLastChild()
            .getLastChild()
            .getLastChild()
            .getLastChild() : null;
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    private static DlangFile createFileFromText(@NotNull final Project project,
                                                @NotNull final String text) {
        return (DlangFile) PsiFileFactory.getInstance(project)
            .createFileFromText("A.hs", DLanguage.INSTANCE, text);
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

