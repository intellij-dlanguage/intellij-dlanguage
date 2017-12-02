package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
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
    DLanguageIdentifier getIdentifier();

    @NotNull
    List<DLanguageTypeSuffix> getTypeSuffixs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_EQ();

}
