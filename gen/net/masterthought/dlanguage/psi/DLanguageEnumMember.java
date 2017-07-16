package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageEnumMemberStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumMember extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageEnumMemberStub> {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();
}
