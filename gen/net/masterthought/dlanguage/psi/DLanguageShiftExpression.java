package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageShiftExpression extends PsiElement {
    @Nullable
    public DLanguageShiftExpression getShiftExpression();

    @Nullable
    public DLanguageAddExpression getAddExpression();

    @Nullable
    public PsiElement getOP_SH_RIGHT();

    @Nullable
    public PsiElement getOP_SH_LEFT();

    @Nullable
    public PsiElement getOP_USH_RIGHT();

}
