package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.stubs.DlangIfConditionStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIfCondition extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangIfConditionStub> {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getKW_AUTO();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getOP_EQ();

}
