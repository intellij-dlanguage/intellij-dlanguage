package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageImportExpression extends PsiElement {
    @Nullable
    public DLanguageImportExpression getImportExpression();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
