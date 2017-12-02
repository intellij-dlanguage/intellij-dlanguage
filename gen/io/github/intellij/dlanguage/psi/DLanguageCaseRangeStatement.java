package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCaseRangeStatement extends PsiElement {

    @NotNull
    public List<PsiElement> getKW_CASEs();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

    @NotNull
    public List<PsiElement> getOP_COLONs();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
