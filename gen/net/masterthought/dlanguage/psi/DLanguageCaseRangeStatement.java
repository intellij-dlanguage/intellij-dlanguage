package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageCaseRangeStatement extends PsiElement {
    @NotNull
    List<PsiElement> getKW_CASEs();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

    @NotNull
    List<PsiElement> getOP_COLONs();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageDeclarationsAndStatements getDeclarationsAndStatements();
}
