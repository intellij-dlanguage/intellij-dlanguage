package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Performs creation of element types.
 */
public class DElementFactory {

    /**
     * Takes a name and returns a Psi node of that name, or null.
     */
    @Nullable
    public static DLanguageIdentifier createDLanguageIdentifierFromText(@NotNull Project project, @NotNull String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageIdentifier) return (DLanguageIdentifier) e;
        return null;
    }

    @Nullable
    public static DLanguageFuncDeclaration createDLanguageFuncDeclarationFromText(@NotNull Project project, @NotNull String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageFuncDeclaration) return (DLanguageFuncDeclaration) e;
        return null;
    }

    @Nullable
    public static DLanguageClassDeclaration createDLanguageClassDeclarationFromText(@NotNull Project project, @NotNull String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageClassDeclaration) return (DLanguageClassDeclaration) e;
        return null;
    }

//    /**
//     * Takes a name and returns a Psi node of that name, or null.
//     */
//    @NotNull
//    public static HaskellTycon createTyconFromText(@NotNull Project project, @NotNull String name) {
//        return ((HaskellTycon) (createExpressionFromText(project, name + "uniq = " + name)).getFirstChild());
//    }
//
//    /**
//     * Takes a name and returns a Psi node of that name, or null.
//     */
//    @NotNull
//    public static HaskellTycls createTyclsFromText(@NotNull Project project, @NotNull String name) {
//        return ((HaskellTycls) (createExpressionFromText(project, name + "uniq = " + name)).getFirstChild());
//    }
//
//    @NotNull
//    public static HaskellPpragma createPpragmaFromText(@NotNull Project project, @NotNull String text) {
//        return ((HaskellPpragma) (createFileFromText(project, text + "\nmodule Foo where").getFirstChild()));
//    }
//
//    @NotNull
//    public static HaskellGendecl createGendeclFromText(@NotNull Project project, @NotNull String text) {
//        return ((HaskellGendecl) (createFileFromText(project, text).getFirstChild().getFirstChild()));
//    }
//
//    @NotNull
//    public static PsiWhiteSpace createNewLine(@NotNull Project project) {
//        return ((PsiWhiteSpace) (createFileFromText(project, "\n")).getFirstChild());
//    }

    /**
     * Takes an expression in text and returns a Psi tree of that program.
     */
    @NotNull
    private static PsiElement createExpressionFromText(@NotNull Project project, @NotNull String name) {
        final DLanguageFile fileFromText = createFileFromText(project, name);
        final PsiElement rhs = fileFromText.getFirstChild().getFirstChild().getLastChild();
        return rhs.getLastChild().getLastChild().getLastChild();
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    private static DLanguageFile createFileFromText(@NotNull Project project, @NotNull String text) {
        return (DLanguageFile) PsiFileFactory.getInstance(project).createFileFromText("A.hs", DLanguage.INSTANCE, text);
    }

    public static PsiElement createDLanguageTemplateDeclarationFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageTemplateDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageConstructorFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageConstructor) return e;
        return null;
    }

    public static PsiElement createDLanguageDestructorFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageDestructor) return e;
        return null;
    }

    public static PsiElement createDLanguageStructDeclarationFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageStructDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageAliasDeclarationFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageAliasDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageModuleFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageModuleDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageInterfaceDeclarationFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageInterfaceDeclaration) return e;
        return null;
    }

    public static PsiElement createDLanguageVarDeclarationFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageVarDeclarations) return e;
        return null;
    }

    public static PsiElement createDLanguageLabeledStatementFromText(Project project, String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageLabeledStatement) return e;
        return null;
    }
}

