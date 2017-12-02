package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
