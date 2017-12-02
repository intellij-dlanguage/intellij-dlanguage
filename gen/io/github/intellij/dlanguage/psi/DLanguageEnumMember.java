package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumMember extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageEnumMemberStub> {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();
}
