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
    public List<DLanguageParameterAttribute> getParameterAttributes();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DlangIdentifier getIdentifier();

    @NotNull
    public List<DLanguageTypeSuffix> getTypeSuffixs();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_EQ();

}
