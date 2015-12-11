// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageFuncDeclaration extends DNamedElement {

    @NotNull
    String getName();

    @Nullable
    PsiElement getNameIdentifier();

    @Nullable
    PsiElement setName(String newName);

    @NotNull
    ItemPresentation getPresentation();

    @Nullable
    DLanguageAutoFuncDeclaration getAutoFuncDeclaration();

    @Nullable
    DLanguageBasicType getBasicType();

    @Nullable
    DLanguageFuncDeclarator getFuncDeclarator();

    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @Nullable
    DLanguageStorageClasses getStorageClasses();

    @Nullable
    PsiElement getOpEq();

    @Nullable
    PsiElement getOpScolon();

}
