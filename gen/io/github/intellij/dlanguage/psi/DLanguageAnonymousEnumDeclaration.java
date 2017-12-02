package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAnonymousEnumDeclaration extends PsiElement {

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getKW_ENUM();

    @Nullable
    public DLanguageType getType();

    @NotNull
    public List<DLanguageEnumMember> getEnumMembers();
}
