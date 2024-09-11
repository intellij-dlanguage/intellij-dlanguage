package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageConstraint;
import io.github.intellij.dlanguage.psi.DLanguageStructBody;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType;
import io.github.intellij.dlanguage.stubs.DLanguageStructDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStructDeclaration extends PsiElement, DNamedElement, Declaration, UserDefinedType,
    StubBasedPsiElement<DLanguageStructDeclarationStub>, HasMembers<DLanguageStructDeclarationStub> {
    @Nullable
    PsiElement getKW_STRUCT();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageStructBody getStructBody();

    @Nullable
    PsiElement getOP_SCOLON();
}
