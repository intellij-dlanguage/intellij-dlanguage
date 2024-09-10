
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageParameterAttribute;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeSuffix;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DLanguageParameterStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageParameter extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageParameterStub> {

    @NotNull
    List<DLanguageParameterAttribute> getParameterAttributes();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getIdentifier();

    @NotNull
    List<DLanguageTypeSuffix> getTypeSuffixs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_EQ();
    @Nullable
    PsiElement getKW_IMMUTABLE();
    @Nullable
    PsiElement getKW_SHARED();
    @Nullable
    PsiElement getKW_FINAL();
    @Nullable
    PsiElement getKW_CONST();
    @Nullable
    PsiElement getKW_INOUT();
    @Nullable
    PsiElement getKW_IN();
    @Nullable
    PsiElement getKW_LAZY();
    @Nullable
    PsiElement getKW_OUT();
    @Nullable
    PsiElement getKW_REF();
    @Nullable
    PsiElement getKW_SCOPE();
    @Nullable
    PsiElement getKW_AUTO();
    @Nullable
    PsiElement getKW_RETURN();
}
