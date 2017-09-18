package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DlangEnumMemberStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumMember extends PsiElement, DNamedElement, StubBasedPsiElement<DlangEnumMemberStub> {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageAssignExpression getAssignExpression();
}
