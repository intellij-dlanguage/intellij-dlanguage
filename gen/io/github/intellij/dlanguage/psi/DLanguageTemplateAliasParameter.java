
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateAliasParameter extends PsiElement {

    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    DlangIdentifier getIdentifier();

    @NotNull
    List<DLanguageType> getTypes();

    @NotNull
    List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getOP_EQ();

}
