package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageConstraint;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateDeclaration extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DLanguageTemplateDeclarationStub>, HasMembers<DLanguageTemplateDeclarationStub> {
    @Nullable
    PsiElement getKW_TEMPLATE();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @NotNull
    List<Declaration> getDeclarations();
}
