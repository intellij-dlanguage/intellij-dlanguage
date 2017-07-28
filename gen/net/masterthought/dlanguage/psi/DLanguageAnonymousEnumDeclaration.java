package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
