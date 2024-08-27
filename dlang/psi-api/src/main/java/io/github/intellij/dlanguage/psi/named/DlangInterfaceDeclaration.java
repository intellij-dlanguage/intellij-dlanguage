package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageBaseClassList;
import io.github.intellij.dlanguage.psi.DLanguageConstraint;
import io.github.intellij.dlanguage.psi.DLanguageStructBody;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.stubs.DlangInterfaceDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DlangInterfaceDeclaration extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DlangInterfaceDeclarationStub>, HasMembers<DlangInterfaceDeclarationStub> {
    @Nullable
    PsiElement getKW_INTERFACE();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageStructBody getStructBody();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageBaseClassList getBaseClassList();
}
