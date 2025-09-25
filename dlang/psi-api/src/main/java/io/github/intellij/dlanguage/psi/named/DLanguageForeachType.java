
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageBasicType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement;
import io.github.intellij.dlanguage.psi.interfaces.Expression;
import io.github.intellij.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachType extends PsiElement, DNamedElement, DTypedElement,
    StubBasedPsiElement<DLanguageForeachTypeStub> {

    @Nullable
    PsiElement getKW_REF();

    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    DLanguageBasicType getBasicType();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    Expression getExpression();
}
