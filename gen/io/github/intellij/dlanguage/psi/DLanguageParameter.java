package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangParameterStub;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageParameter extends PsiElement, DNamedElement, StubBasedPsiElement<DlangParameterStub> {
    @NotNull
    List<DLanguageParameterAttribute> getParameterAttributes();

    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @NotNull
    List<DLanguageTypeSuffix> getTypeSuffixs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_EQ();

}
