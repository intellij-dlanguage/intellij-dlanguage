package net.masterthought.dlanguage.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
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
    public static DLanguageFuncDeclaration createDLanguageFuncDeclarationFromText(@NotNull Project project, @NotNull String name) {
        PsiElement e = createExpressionFromText(project, name + "uniq = " + name).getFirstChild();
        if (e instanceof DLanguageFuncDeclaration) return (DLanguageFuncDeclaration) e;
        return null;
    }

    public static DLanguageIdentifier createDLanguageIdentifierFromText(@NotNull Project project, @NotNull String name) {
        return ((DLanguageIdentifier) (createExpressionFromText(project, name + "uniq = " + name)).getFirstChild());
    }

//    public static DLanguageSymbol createDLanguageSymbolFromText(@NotNull Project project, @NotNull String name) {
//        return ((DLanguageSymbol) (createExpressionFromText(project, name + "uniq = " + name)).getFirstChild());
//    }



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
    public static PsiElement createExpressionFromText(@NotNull Project project, @NotNull String name) {
        DLanguageFile fileFromText = createFileFromText(project, name);
        PsiElement rhs = fileFromText.getFirstChild().getFirstChild().getLastChild();
        PsiElement nodeOfInterest = rhs.getLastChild().getLastChild().getLastChild();
        return nodeOfInterest;
    }

    /**
     * Create a file containing text.
     */
    @NotNull
    public static DLanguageFile createFileFromText(@NotNull Project project, @NotNull String text) {
        return (DLanguageFile) PsiFileFactory.getInstance(project).createFileFromText("A.hs", DLanguage.INSTANCE, text);
    }

}

