package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageIfConditionStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIfCondition extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageIfConditionStub> {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getKW_AUTO();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getOP_EQ();

}
