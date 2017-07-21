package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageParameter extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageParameterStub> {
    @NotNull
    public List<DLanguageParameterAttribute> getParameterAttributes();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @NotNull
    public List<DLanguageTypeSuffix> getTypeSuffixs();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_EQ();

}
