
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageEnumMemberAttribute;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement;
import io.github.intellij.dlanguage.psi.interfaces.Expression;
import io.github.intellij.dlanguage.stubs.DLanguageEnumMemberStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageEnumMember extends PsiElement, DNamedElement, DTypedElement,
    StubBasedPsiElement<DLanguageEnumMemberStub> {

    @NotNull
    List<DLanguageEnumMemberAttribute> getEnumMemberAttributes();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageType getType();

    @Nullable
    Expression getExpression();
}
