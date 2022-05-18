package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangEnumMember;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAnonymousEnumDeclaration extends PsiElement {

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_COLON();

    @NotNull
    PsiElement getKW_ENUM();

    @NotNull
    PsiElement getOP_PAR_LEFT();

    @NotNull
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageType getType();

    @NotNull
    List<DlangEnumMember> getEnumMembers();
}
